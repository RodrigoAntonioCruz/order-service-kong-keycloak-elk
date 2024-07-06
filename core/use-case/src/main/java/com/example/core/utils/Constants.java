package com.example.core.utils;


public class Constants {
    private Constants() {
    }
    public static final String INVALID_FILE_NAME = "O arquivo deve ser um arquivo de texto (.txt)";
    public static final String NOT_NULL_FILE = "O arquivo não pode ser nulo";
    public static final String INVALID_LINE_FILE = "A linha %s do arquivo é inválida";
    public static final String ERROR_CONVERTER_LINE = "Erro ao converter linha %s, erro: %s";
    public static final String ERROR_SEND_FILE = "Erro ao enviar pedido para Kafka";
    public static final String ERROR_READ_FILE = "Erro ao ler o arquivo";
    public static final String TXT = ".txt";
    public static final String INVALID_DATE = "A data do pedido não pode ser nula ou futura";
    public static final String INVALID_TOTAL_VALUE = "O valor do pedido deve ser maior que zero";
    public static final String PRODUCT_NOT_FOUND = "Produto %s não encontrado";
    public static final String USER_NOT_FOUND = "Usuário %s não encontrado";
    public static final String ORDER_ID = "{\"orderId\":\"%s\"}";

}
