package com.candido.server.persistence.candido;

import com.zaxxer.hikari.HikariConfig;

public class CandidoDataSourceConfigurationConstant extends HikariConfig {
    public final static String CONFIGURATION_PROPERTIES = "app.datasource.mariadb.candido";
    public final static String REPOSITORY_BASE_PACKAGES = "com.candido.server.repository.candido";
    public final static String ENTITY_MANAGER = "candido_entity_manager";
    public final static String TRANSACTION_MANAGER = "candido_transaction_manager";
    public final static String DATASOURCE = "candido_datasource";
    public final static String PERSISTENCE_UNIT_NAME = "candido";
    public final static String MODEL_PACKAGE = "com.candido.server.model.crm";
}
