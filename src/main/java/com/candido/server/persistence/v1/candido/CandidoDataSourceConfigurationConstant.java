package com.candido.server.persistence.v1.candido;

import com.zaxxer.hikari.HikariConfig;

public class CandidoDataSourceConfigurationConstant extends HikariConfig {
    public static final String CONFIGURATION_PROPERTIES = "app.datasource.mariadb.candido";
    public static final String REPOSITORY_BASE_PACKAGES = "com.candido.server.repository.v1.candido";
    public static final String ENTITY_MANAGER = "candido_entity_manager";
    public static final String TRANSACTION_MANAGER = "candido_transaction_manager";
    public static final String DATASOURCE = "candido_datasource";
    public static final String PERSISTENCE_UNIT_NAME = "candido";
    public static final String MODEL_PACKAGE = "com.candido.server.domain.v1.candido";
}
