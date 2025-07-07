CREATE TABLE opportunity_type (
    opportunity_type_id BIGSERIAL PRIMARY KEY,
    key VARCHAR(64) UNIQUE NOT NULL,
    label VARCHAR(128)
);

CREATE TABLE opportunity_status (
    opportunity_status_id BIGSERIAL PRIMARY KEY,
    key VARCHAR(64) UNIQUE NOT NULL,
    label VARCHAR(128)
);

CREATE TABLE location_type (
    location_type_id BIGSERIAL PRIMARY KEY,
    key VARCHAR(32) UNIQUE NOT NULL,
    label VARCHAR(128)
);

CREATE TABLE currency (
    currency_id BIGSERIAL PRIMARY KEY,
    code VARCHAR(3) UNIQUE NOT NULL,
    name VARCHAR(64) NOT NULL,
    symbol VARCHAR(8),
    enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE tag (
    tag_id BIGSERIAL PRIMARY KEY,
    key VARCHAR(64) UNIQUE NOT NULL,
    label VARCHAR(128) NOT NULL
);

CREATE TABLE opportunity_tag (
    fk_opportunity_id BIGINT NOT NULL REFERENCES opportunity(opportunity_id) ON DELETE CASCADE,
    fk_tag_id BIGINT NOT NULL REFERENCES tag(tag_id) ON DELETE CASCADE,
    PRIMARY KEY (fk_opportunity_id, fk_tag_id)
);

CREATE TABLE opportunity_level (
    opportunity_level_id BIGSERIAL PRIMARY KEY,
    key VARCHAR(32) UNIQUE NOT NULL,
    label VARCHAR(64) NOT NULL
);

INSERT INTO opportunity_type (key, label) VALUES
    ('bootcamp', 'Bootcamp'),
    ('internship', 'Internship'),
    ('challenge', 'Challenge'),
    ('job', 'Job'),
    ('thesis', 'Thesis'),
    ('volunteering', 'Volunteering'),
    ('research', 'Research Collaboration');

INSERT INTO opportunity_status (key, label) VALUES
    ('draft', 'Draft'),
    ('published', 'Published'),
    ('closed', 'Closed'),
    ('archived', 'Archived');

INSERT INTO location_type (key, label) VALUES
    ('remote', 'Remote'),
    ('on-site', 'On-site'),
    ('hybrid', 'Hybrid');

INSERT INTO currency (code, name, symbol) VALUES
    ('EUR', 'Euro', '€'),
    ('USD', 'US Dollar', '$'),
    ('GBP', 'British Pound', '£'),
    ('JPY', 'Japanese Yen', '¥'),
    ('CHF', 'Swiss Franc', 'CHF');

INSERT INTO tag (key, label) VALUES
    ('python', 'Python'),
    ('java', 'Java'),
    ('javascript', 'JavaScript'),
    ('web-development', 'Web Development'),
    ('data-analysis', 'Data Analysis'),
    ('machine-learning', 'Machine Learning'),
    ('cloud-computing', 'Cloud Computing'),
    ('cybersecurity', 'Cybersecurity'),
    ('mobile-development', 'Mobile Development'),
    ('devops', 'DevOps'),
    ('agile', 'Agile'),
    ('full-stack', 'Full Stack'),
    ('backend', 'Backend'),
    ('frontend', 'Frontend'),
    ('open-source', 'Open Source'),
    ('artificial-intelligence', 'Artificial Intelligence'),
    ('blockchain', 'Blockchain'),
    ('big-data', 'Big Data'),
    ('software-engineering', 'Software Engineering'),
    ('data-visualization', 'Data Visualization'),
    ('user-experience', 'User Experience'),
    ('product-management', 'Product Management'),
    ('business-intelligence', 'Business Intelligence'),
    ('quality-assurance', 'Quality Assurance'),
    ('agile-methodologies', 'Agile Methodologies'),
    ('scrum', 'Scrum'),
    ('kotlin', 'Kotlin'),
    ('swift', 'Swift'),
    ('ruby', 'Ruby'),
    ('php', 'PHP'),
    ('c-sharp', 'C#'),
    ('c-plus-plus', 'C++'),
    ('golang', 'Go'),
    ('rust', 'Rust'),
    ('sql', 'SQL'),
    ('no-sql', 'NoSQL'),
    ('data-engineering', 'Data Engineering'),
    ('machine-learning-engineering', 'Machine Learning Engineering'),
    ('artificial-intelligence-engineering', 'Artificial Intelligence Engineering'),
    ('cloud-architecture', 'Cloud Architecture'),
    ('cybersecurity-engineering', 'Cybersecurity Engineering'),
    ('mobile-app-development', 'Mobile App Development'),
    ('web-design', 'Web Design'),
    ('user-interface', 'User Interface'),
    ('user-research', 'User Research'),
    ('data-mining', 'Data Mining'),
    ('data-wrangling', 'Data Wrangling'),
    ('data-engineering-tools', 'Data Engineering Tools'),
    ('data-science-tools', 'Data Science Tools'),
    ('business-analytics', 'Business Analytics'),
    ('digital-marketing', 'Digital Marketing'),
    ('seo', 'SEO'),
    ('content-creation', 'Content Creation'),
    ('social-media-management', 'Social Media Management'),
    ('project-management', 'Project Management'),
    ('agile-project-management', 'Agile Project Management'),
    ('scrum-master', 'Scrum Master'),
    ('product-owner', 'Product Owner'),
    ('business-development', 'Business Development'),
    ('sales', 'Sales'),
    ('customer-support', 'Customer Support'),
    ('technical-support', 'Technical Support'),
    ('network-administration', 'Network Administration'),
    ('system-administration', 'System Administration'),
    ('database-administration', 'Database Administration'),
    ('it-security', 'IT Security'),
    ('it-auditing', 'IT Auditing'),
    ('it-compliance', 'IT Compliance'),
    ('it-governance', 'IT Governance'),
    ('it-service-management', 'IT Service Management'),
    ('it-operations', 'IT Operations'),
    ('it-project-management', 'IT Project Management'),
    ('it-consulting', 'IT Consulting'),
    ('it-training', 'IT Training'),
    ('it-certification', 'IT Certification'),
    ('it-research', 'IT Research'),
    ('it-development', 'IT Development'),
    ('it-architecture', 'IT Architecture'),
    ('it-infrastructure', 'IT Infrastructure'),
    ('it-innovation', 'IT Innovation'),
    ('it-strategy', 'IT Strategy'),
    ('it-business-alignment', 'IT Business Alignment'),
    ('it-risk-management', 'IT Risk Management'),
    ('it-change-management', 'IT Change Management'),
    ('it-service-delivery', 'IT Service Delivery'),
    ('it-service-support', 'IT Service Support'),
    ('it-service-design', 'IT Service Design'),
    ('it-service-transition', 'IT Service Transition'),
    ('it-service-operation', 'IT Service Operation'),
    ('it-service-improvement', 'IT Service Improvement'),
    ('it-service-automation', 'IT Service Automation'),
    ('it-service-orchestration', 'IT Service Orchestration'),
    ('it-service-monitoring', 'IT Service Monitoring'),
    ('it-service-reporting', 'IT Service Reporting'),
    ('it-service-analytics', 'IT Service Analytics'),
    ('it-service-performance', 'IT Service Performance'),
    ('it-service-quality', 'IT Service Quality'),
    ('it-service-security', 'IT Service Security'),
    ('it-service-compliance', 'IT Service Compliance'),
    ('it-service-governance', 'IT Service Governance'),
    ('it-service-architecture', 'IT Service Architecture'),
    ('it-service-integration', 'IT Service Integration'),
    ('it-service-delivery-models', 'IT Service Delivery Models'),
    ('it-service-management-tools', 'IT Service Management Tools'),
    ('it-service-management-processes', 'IT Service Management Processes'),
    ('it-service-management-frameworks', 'IT Service Management Frameworks'),
    ('it-service-management-standards', 'IT Service Management Standards'),
    ('it-service-management-best-practices', 'IT Service Management Best Practices'),
    ('it-service-management-certifications', 'IT Service Management Certifications'),
    ('it-service-management-training', 'IT Service Management Training'),
    ('it-service-management-consulting', 'IT Service Management Consulting'),
    ('it-service-management-research', 'IT Service Management Research'),
    ('it-service-management-innovation', 'IT Service Management Innovation'),
    ('it-service-management-strategy', 'IT Service Management Strategy'),
    ('data-science', 'Data Science');

INSERT INTO opportunity_level (key, label) VALUES
    ('internship', 'Internship'),
    ('entry', 'Entry Level'),
    ('mid', 'Mid Level'),
    ('senior', 'Senior Level'),
    ('lead', 'Lead / Principal'),
    ('manager', 'Manager'),
    ('director', 'Director'),
    ('executive', 'Executive / C-Level');
