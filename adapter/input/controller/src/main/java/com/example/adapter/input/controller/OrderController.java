package com.example.adapter.input.controller;


import com.example.adapter.input.controller.dto.OrderDTO;
import com.example.adapter.input.controller.dto.OrdersDTO;
import com.example.adapter.input.controller.mapper.OrderInputMapper;
import com.example.adapter.input.controller.utils.Constants;
import com.example.adapter.input.controller.utils.PageUtils;
import com.example.core.ports.input.FindOrderByFilterInputPort;
import com.example.core.ports.input.UploadOrderFileInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Order")
@RequestMapping("/orders")
public class OrderController {

    private final OrderInputMapper mapper;

    private final UploadOrderFileInputPort uploadOrderFileInputPort;

    private final FindOrderByFilterInputPort findOrderByFilterInputPort;


    /**
     *
     * Esse é um teste de CK ffdfdsfff
     *
     * **/
    @PostMapping(value = "/upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Realiza upload de um arquivo de texto com pedidos para processamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Inconsistência nos dados informados"),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado"),
            @ApiResponse(responseCode = "500", description = "Sistema indisponível no momento")})
    public ResponseEntity<Void> uploadOrderFile(@RequestParam(name="file") MultipartFile file) throws IOException {
        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Início do upload de um arquivo de pedidos ", Constants.LOG_METHOD_CREATE, file.getOriginalFilename());

            uploadOrderFileInputPort.upload(file.getInputStream(), file.getOriginalFilename());

        log.info(Constants.LOG_KEY_MESSAGE + Constants.LOG_KEY_METHOD + Constants.LOG_KEY_ENTITY,
                "Fim do upload de um arquivo de pedidos ", Constants.LOG_METHOD_CREATE, file.getOriginalFilename());

        return ResponseEntity.noContent().build();
    }
}
