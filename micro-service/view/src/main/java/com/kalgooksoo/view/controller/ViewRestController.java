package com.kalgooksoo.view.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 뷰 REST 컨트롤러
 */
@Tag(name = "ViewRestController", description = "뷰 API")
@RestController
@RequestMapping("/views")
@RequiredArgsConstructor
public class ViewRestController {

}