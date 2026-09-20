package com.sagar.redispoc.service;

import com.sagar.redispoc.entity.Document;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private static final String DOC_KEY_PREFIX = "poc:doc:";
    private static final String DOC_INDEX_KEY = "poc:doc:index";

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Save a document into Redis
     */
    public Document save(Document doc) {
        if (doc.getId() == null) {
            doc.setId(UUID.randomUUID().toString());
        }
        doc.setCreatedAt(LocalDateTime.now());

        String key = DOC_KEY_PREFIX + doc.getId();
        redisTemplate.opsForValue().set(key, doc, 1, TimeUnit.HOURS);

        // Add id to an index set so we can list all documents
        redisTemplate.opsForSet().add(DOC_INDEX_KEY, doc.getId());

        log.info(">>> Document saved to Redis: {}", key);
        return doc;
    }

    /**
     * Read a document by id
     */
    public Document get(String id) {
        Object obj = redisTemplate.opsForValue().get(DOC_KEY_PREFIX + id);
        return obj == null ? null : (Document) obj;
    }

    /**
     * List all documents
     */
    public List<Document> getAll() {
        Set<Object> ids = redisTemplate.opsForSet().members(DOC_INDEX_KEY);
        if (ids == null) return Collections.emptyList();

        List<Document> docs = new ArrayList<>();
        for (Object id : ids) {
            Object obj = redisTemplate.opsForValue().get(DOC_KEY_PREFIX + id);
            if (obj != null) {
                docs.add((Document) obj);
            } else {
                // Clean up dangling index entries
                redisTemplate.opsForSet().remove(DOC_INDEX_KEY, id);
            }
        }
        return docs;
    }

    /**
     * Delete a document
     */
    public void delete(String id) {
        redisTemplate.delete(DOC_KEY_PREFIX + id);
        redisTemplate.opsForSet().remove(DOC_INDEX_KEY, id);
        log.info(">>> Document deleted from Redis: {}", id);
    }

    /**
     * Expire a document after N seconds
     */
    public void setTtl(String id, long seconds) {
        redisTemplate.expire(DOC_INDEX_KEY + id, Expiration.seconds(seconds));
    }
}
