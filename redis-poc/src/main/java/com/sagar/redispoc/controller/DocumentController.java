package com.sagar.redispoc.controller;

import com.sagar.redispoc.entity.Document;
import com.sagar.redispoc.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public Document save(@RequestBody Document doc) {
        return documentService.save(doc);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> get(@PathVariable String id) {
        Document doc = documentService.get(id);
        return doc == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(doc);
    }

    @GetMapping
    public List<Document> all() {
        return documentService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ttl/{seconds}")
    public ResponseEntity<String> setTtl(@PathVariable String id,
                                         @PathVariable long seconds) {
        documentService.setTtl(id, seconds);
        return ResponseEntity.ok("TTL set to " + seconds + "s");
    }
}
