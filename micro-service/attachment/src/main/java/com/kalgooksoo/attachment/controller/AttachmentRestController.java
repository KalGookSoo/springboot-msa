package com.kalgooksoo.attachment.controller;

import com.kalgooksoo.attachment.service.AttachmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AttachmentRestController", description = "첨부파일 API")
@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
public class AttachmentRestController {

    private final AttachmentService attachmentService;

}