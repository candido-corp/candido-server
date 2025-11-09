CREATE TABLE tag_category (
    tag_category_id BIGSERIAL PRIMARY KEY,
    key VARCHAR(64) UNIQUE NOT NULL,
    label VARCHAR(128) NOT NULL
);

INSERT INTO tag_category (key, label) VALUES
    ('language', 'Language'),
    ('technology', 'Technology'),
    ('tool', 'Tool'),
    ('field', 'Field'),
    ('framework', 'Framework'),
    ('role', 'Role'),
    ('process', 'Process'),
    ('department', 'Department'),
    ('design', 'Design'),
    ('content', 'Content'),
    ('admin', 'Administration'),
    ('management', 'Management'),
    ('quality', 'Quality'),
    ('education', 'Education'),
    ('research', 'Research'),
    ('analytics', 'Analytics'),
    ('devops', 'DevOps'),
    ('open-source', 'Open Source'),
    ('uncategorized', 'Uncategorized');

ALTER TABLE tag ADD COLUMN fk_tag_category_id BIGINT REFERENCES tag_category(tag_category_id);

UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'python';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'java';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'javascript';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'technology') WHERE key = 'web-development';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'field') WHERE key = 'data-analysis';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'field') WHERE key = 'machine-learning';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'technology') WHERE key = 'cloud-computing';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'technology') WHERE key = 'cybersecurity';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'technology') WHERE key = 'mobile-development';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'devops') WHERE key = 'devops';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'framework') WHERE key = 'agile';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'role') WHERE key = 'full-stack';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'role') WHERE key = 'backend';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'role') WHERE key = 'frontend';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'open-source') WHERE key = 'open-source';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'field') WHERE key = 'artificial-intelligence';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'technology') WHERE key = 'blockchain';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'technology') WHERE key = 'big-data';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'field') WHERE key = 'software-engineering';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'analytics') WHERE key = 'data-visualization';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'design') WHERE key = 'user-experience';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'role') WHERE key = 'product-management';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'field') WHERE key = 'business-intelligence';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'quality') WHERE key = 'quality-assurance';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'framework') WHERE key = 'agile-methodologies';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'framework') WHERE key = 'scrum';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'kotlin';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'swift';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'ruby';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'php';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'c-sharp';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'c-plus-plus';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'golang';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'rust';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'sql';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'language') WHERE key = 'no-sql';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'technology') WHERE key = 'data-engineering';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'machine-learning-engineering';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'artificial-intelligence-engineering';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'technology') WHERE key = 'cloud-architecture';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'field') WHERE key = 'cybersecurity-engineering';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'mobile-app-development';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'design') WHERE key = 'web-design';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'design') WHERE key = 'user-interface';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'design') WHERE key = 'user-research';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'analytics') WHERE key = 'data-mining';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'analytics') WHERE key = 'data-wrangling';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'tool') WHERE key = 'data-engineering-tools';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'tool') WHERE key = 'data-science-tools';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'field') WHERE key = 'business-analytics';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'field') WHERE key = 'digital-marketing';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'tool') WHERE key = 'seo';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'content') WHERE key = 'content-creation';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'content') WHERE key = 'social-media-management';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'management') WHERE key = 'project-management';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'agile-project-management';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'framework') WHERE key = 'scrum-master';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'role') WHERE key = 'product-owner';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'management') WHERE key = 'business-development';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'role') WHERE key = 'sales';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'role') WHERE key = 'customer-support';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'role') WHERE key = 'technical-support';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'admin') WHERE key = 'network-administration';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'admin') WHERE key = 'system-administration';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'admin') WHERE key = 'database-administration';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'department') WHERE key = 'it-security';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-auditing';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'department') WHERE key = 'it-compliance';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'department') WHERE key = 'it-governance';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'process') WHERE key = 'it-service-management';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'devops') WHERE key = 'it-operations';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'process') WHERE key = 'it-project-management';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'department') WHERE key = 'it-consulting';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'education') WHERE key = 'it-training';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'education') WHERE key = 'it-certification';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'research') WHERE key = 'it-research';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-development';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'department') WHERE key = 'it-architecture';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'department') WHERE key = 'it-infrastructure';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'department') WHERE key = 'it-innovation';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'department') WHERE key = 'it-strategy';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-business-alignment';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-risk-management';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'process') WHERE key = 'it-change-management';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'process') WHERE key = 'it-service-delivery';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'process') WHERE key = 'it-service-support';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-design';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'process') WHERE key = 'it-service-transition';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'process') WHERE key = 'it-service-operation';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'process') WHERE key = 'it-service-improvement';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'devops') WHERE key = 'it-service-automation';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'devops') WHERE key = 'it-service-orchestration';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-monitoring';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-reporting';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'analytics') WHERE key = 'it-service-analytics';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'quality') WHERE key = 'it-service-performance';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'quality') WHERE key = 'it-service-quality';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-security';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-compliance';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-governance';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-architecture';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-integration';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-delivery-models';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'management') WHERE key = 'it-service-management-tools';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'management') WHERE key = 'it-service-management-processes';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'management') WHERE key = 'it-service-management-frameworks';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'management') WHERE key = 'it-service-management-standards';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-management-best-practices';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-management-certifications';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-management-training';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-management-consulting';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'research') WHERE key = 'it-service-management-research';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-management-innovation';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'uncategorized') WHERE key = 'it-service-management-strategy';
UPDATE tag SET fk_tag_category_id = (SELECT tag_category_id FROM tag_category WHERE key = 'technology') WHERE key = 'data-science';