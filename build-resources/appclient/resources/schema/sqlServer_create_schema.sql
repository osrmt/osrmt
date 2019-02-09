
create table app_control_template (
   app_control_template_id       int default 0 not null
   CONSTRAINT app_control_template_pk PRIMARY KEY ,
   application_view_id           int default 0 not null,
   display_sequence              int default 0 not null,
   control_ref_id                int default 0 not null,
   control_type_ref_id           int default 0 not null,
   control_text                  varchar(100),
   control_description           varchar(255),
   model_column_ref_id           int default 0 not null,
   application_custom_control_id int default 0 not null,
   app_control_user_defined_id   int default 0 not null,
   control_format                varchar(50),
   source_ref_id                 int default 0 not null,
   default_value                 varchar(50),
   locked_ind                    int default 0 not null,
   disabled_ind                  int default 0 not null,
   required_ind                  int default 0 not null,
   visible_ind                   int default 1 not null,
   init_script                   varchar(255),
   focus_lost_script             text,
   focus_gained_script           text,
   image_path                    varchar(255),
   scrollpane_ind                int default 0 not null,
   grow_height                   decimal default 0 not null,
   grow_width                    int default 1 not null,
   unit_width                    int default 1 not null,
   unit_height                   int default 1 not null,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index app_control_template_uk0 on app_control_template (app_control_template_id
);
create index app_control_template_fk2 on app_control_template (control_ref_id
);
create index app_control_template_fk3 on app_control_template (control_type_ref_id
);
create index app_control_template_fk4 on app_control_template (source_ref_id
);
create index app_control_template_fk7 on app_control_template (app_control_user_defined_id
);
create index app_control_template_fk8 on app_control_template (application_custom_control_id
);
create index app_control_template_fk9 on app_control_template (application_view_id
);
create table application_control (
   application_control_id        int default 0 not null
   CONSTRAINT application_control_pk PRIMARY KEY ,
   application_view_id           int default 0 not null,
   display_sequence              int default 0 not null,
   control_ref_id                int default 0 not null,
   control_type_ref_id           int default 0 not null,
   control_text                  varchar(100),
   control_description           varchar(255),
   model_column_ref_id           int default 0 not null,
   application_custom_control_id int default 0 not null,
   app_control_user_defined_id   int default 0 not null,
   control_format                varchar(50),
   source_ref_id                 int default 0 not null,
   default_value                 varchar(50),
   locked_ind                    int default 0 not null,
   disabled_ind                  int default 0 not null,
   required_ind                  int default 0 not null,
   visible_ind                   int default 1 not null,
   init_script                   varchar(255),
   focus_lost_script             text,
   focus_gained_script           text,
   image_path                    varchar(255),
   scrollpane_ind                int default 0 not null,
   grow_height                   decimal default 0 not null,
   grow_width                    int default 1 not null,
   unit_width                    int default 1 not null,
   unit_height                   int default 1 not null,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index application_control_uk0 on application_control (application_control_id
);
create index application_control_fk2 on application_control (control_ref_id
);
create index application_control_fk3 on application_control (control_type_ref_id
);
create index application_control_fk4 on application_control (source_ref_id
);
create index application_control_fk7 on application_control (app_control_user_defined_id
);
create index application_control_fk8 on application_control (application_custom_control_id
);
create index application_control_fk9 on application_control (application_view_id
);
create table application_custom_control (
   application_custom_control_id int default 0
   CONSTRAINT application_custom_control_pk PRIMARY KEY ,
   custom_control_ref_id         int default 0,
   class_name                    varchar(255),
   populate_script               text,
   html_script                   text,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index application_custom_control_uk0 on application_custom_control (application_custom_control_id
);
create table application_security (
   application_security_id       int default 0 not null
   CONSTRAINT application_security_pk PRIMARY KEY ,
   table_key_id                  int default 0 not null,
   table_ref_id                  int default 0 not null,
   application_view_id           int default 0 not null,
   read_only_ind                 int default 0 not null,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index application_security_uk0 on application_security (application_security_id
);
create index application_security_fk3 on application_security (application_view_id
);
create index application_security_k1 on application_security (table_key_id, table_ref_id
);
create table application_setting (
   application_setting_id        int default 0 not null
   CONSTRAINT application_setting_pk PRIMARY KEY ,
   table_key_id                  int default 0 not null,
   table_ref_id                  int default 0 not null,
   application_view_id           int default 0 not null,
   setting_ref_id                int default 0 not null,
   data_type_ref_id              int default 0 not null,
   value_int                     int,
   value_double                  decimal,
   value_string                  text,
   value_date                    datetime,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index application_setting_uk0 on application_setting (application_setting_id
);
create index application_setting_fk1 on application_setting (application_view_id
);
create index application_setting_fk2 on application_setting (setting_ref_id
);
create table application_user (
   user_id                       int default 0
   CONSTRAINT application_user_pk PRIMARY KEY ,
   first_name                    varchar(50),
   last_name                     varchar(50),
   display_name                  varchar(50),
   user_login                    varchar(50) not null,
   password                      varchar(50),
   password_reset_ind            int default 0 not null,
   email                         varchar(255),
   position_ref_id               int default 0 not null,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index application_user_uk0 on application_user (user_id
);
create unique index application_user_uk1 on application_user (user_login
);
create index application_user_fk1 on application_user (position_ref_id
);
create table application_user_group (
   application_user_group_id     int default 0
   CONSTRAINT application_user_group_pk PRIMARY KEY ,
   user_id                       int default 0 not null,
   user_group_ref_id             int default 0 not null,
   email                         varchar(255),
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index application_user_group_uk0 on application_user_group (application_user_group_id
);
create unique index application_user_group_uk1 on application_user_group (user_group_ref_id, user_id
);
create index application_user_group_fk1 on application_user_group (user_id
);
create table application_view (
   application_view_id           int default 0 not null
   CONSTRAINT application_view_pk PRIMARY KEY ,
   application_ref_id            int default 0 not null,
   app_type_ref_id               int default 0 not null,
   view_ref_id                   int default 0 not null,
   form_title                    varchar(50),
   init_script                   text,
   validation_script             text,
   form_width                    int default 0,
   form_height                   int default 0,
   form_center_screen_ind        int default 0,
   form_x_pos                    int default 0,
   form_y_pos                    int default 0,
   form_control_columns          int default 0,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index application_view_uk0 on application_view (application_view_id
);
create unique index application_view_uk1 on application_view (application_ref_id, app_type_ref_id, view_ref_id
);
create index application_view_fk1 on application_view (app_type_ref_id
);
create index application_view_fk2 on application_view (application_ref_id
);
create index application_view_fk3 on application_view (view_ref_id
);
create table artifact (
   artifact_id                   int default 0
   CONSTRAINT artifact_pk PRIMARY KEY ,
   product_ref_id                int default 0 not null,
   version_ref_id                int default 0 not null,
   artifact_ref_id               int default 0 not null,
   artifact_nbr                  int default 0 not null,
   artifact_seq                  int default 0 not null,
   artifact_level_ref_id         int default 0 not null,
   component_type_ref_id         int default 0 not null,
   last_updated_baseline_id      int default 0 not null,
   artifact_name                 varchar(255),
   description                   text,
   status_ref_id                 int default 0 not null,
   priority_ref_id               int default 0 not null,
   complexity_ref_id             int default 0 not null,
   author_ref_id                 int default 0 not null,
   assigned_ref_id               int default 0 not null,
   category_ref_id               int default 0 not null,
   assigned_user_id              int default 0,
   assigned_user_group_id        int default 0,
   effort                        decimal default 0 not null,
   rationale                     text,
   origin                        text,
   goal                          text,
   context                       text,
   precondition                  text,
   postcondition                 text,
   summary                       text,
   external_references           varchar(255),
   planned_version_ref_id        int default 0 not null,
   last_modified_version_ref_id  int default 0 not null,
   removed_ind                   int default 0 not null,
   module_ref_id                 int default 0 not null,
   origin_category_ref_id        int default 0 not null,
   change_reason                 varchar(255),
   change_made                   text,
   report_sequence               int default 0,
   report_outline                varchar(255),
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null,
   custom_text1                  varchar(255),
   custom_text2                  varchar(255),
   custom_text3                  varchar(255),
   custom_text4                  varchar(255),
   custom1_ref_id                int default 0,
   custom2_ref_id                int default 0,
   custom3_ref_id                int default 0,
   custom4_ref_id                int default 0,
   custom_date1                  datetime,
   custom_date2                  datetime,
   custom_memo1                  text,
   custom_memo2                  text,
   custom_int1                   int default 0,
   custom_int2                   int default 0,
   custom_double1                decimal default 0
);
create unique index artifact_uk0 on artifact (artifact_id
);
create unique index artifact_uk1 on artifact (artifact_nbr, artifact_ref_id, product_ref_id
);
create index artifact_fk1 on artifact (priority_ref_id
);
create index artifact_fk2 on artifact (artifact_ref_id
);
create index artifact_fk3 on artifact (status_ref_id
);
create index artifact_fk4 on artifact (version_ref_id
);
create index artifact_fk5 on artifact (complexity_ref_id
);
create index artifact_fk6 on artifact (author_ref_id
);
create index artifact_fk7 on artifact (assigned_ref_id
);
create index artifact_fk8 on artifact (product_ref_id
);
create index artifact_fk9 on artifact (category_ref_id
);
create table artifact_document (
   artifact_document_id          int default 0
   CONSTRAINT artifact_document_pk PRIMARY KEY ,
   artifact_id                   int default 0 not null,
   line_seq                      int default 0,
   line_text                     text,
   image_record_file_id          int default 0,
   style_ref_id                  int default 0,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index artifact_document_uk0 on artifact_document (artifact_document_id
);
create unique index artifact_document_uk1 on artifact_document (artifact_id, line_seq
);
create table artifact_history (
   artifact_history_id           int default 0 not null
   CONSTRAINT artifact_history_pk PRIMARY KEY ,
   artifact_id                   int default 0 not null,
   history_dt                    datetime default current_timestamp,
   history_user_id               int default 0 not null,
   baseline_id                   int default 0 not null,
   product_ref_id                int default 0 not null,
   version_ref_id                int default 0 not null,
   artifact_ref_id               int default 0 not null,
   artifact_nbr                  int default 0 not null,
   artifact_seq                  int default 0 not null,
   artifact_level_ref_id         int default 0 not null,
   component_type_ref_id         int default 0 not null,
   last_updated_baseline_id      int default 0 not null,
   artifact_name                 varchar(255),
   description                   text,
   status_ref_id                 int default 0 not null,
   priority_ref_id               int default 0 not null,
   complexity_ref_id             int default 0 not null,
   author_ref_id                 int default 0 not null,
   assigned_ref_id               int default 0 not null,
   category_ref_id               int default 0 not null,
   assigned_user_id              int default 0,
   assigned_user_group_id        int default 0,
   effort                        decimal default 0 not null,
   rationale                     text,
   origin                        text,
   goal                          text,
   context                       text,
   precondition                  text,
   postcondition                 text,
   summary                       text,
   external_references           varchar(255),
   planned_version_ref_id        int default 0 not null,
   last_modified_version_ref_id  int default 0 not null,
   removed_ind                   int default 0 not null,
   module_ref_id                 int default 0 not null,
   origin_category_ref_id        int default 0 not null,
   change_reason                 varchar(255),
   change_made                   text,
   report_sequence               int default 0,
   report_outline                varchar(255),
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null,
   custom_text1                  varchar(255),
   custom_text2                  varchar(255),
   custom_text3                  varchar(255),
   custom_text4                  varchar(255),
   custom1_ref_id                int default 0,
   custom2_ref_id                int default 0,
   custom3_ref_id                int default 0,
   custom4_ref_id                int default 0,
   custom_date1                  datetime,
   custom_date2                  datetime,
   custom_memo1                  text,
   custom_memo2                  text,
   custom_int1                   int default 0,
   custom_int2                   int default 0,
   custom_double1                decimal default 0
);
create unique index artifact_history_uk0 on artifact_history (artifact_history_id
);
create index artifact_history_k1 on artifact_history (artifact_id
);
create index artifact_history_k2 on artifact_history (baseline_id
);
create table artifact_identity (
   artifact_identity_id          int default 0
   CONSTRAINT artifact_identity_pk PRIMARY KEY ,
   source_ref_id                 int default 0 not null,
   identifier                    varchar(255),
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index artifact_identity_uk0 on artifact_identity (artifact_identity_id
);
create table baseline (
   baseline_id                   int default 0
   CONSTRAINT baseline_pk PRIMARY KEY ,
   product_ref_id                int default 0 not null,
   baseline_name                 varchar(255) not null,
   baseline_dt                   datetime default current_timestamp,
   description                   varchar(255),
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index baseline_uk0 on baseline (baseline_id
);
create unique index baseline_uk1 on baseline (product_ref_id, baseline_name
);
create index baseline_uk2 on baseline (baseline_dt, product_ref_id
);
create table issue (
   issue_id                      int default 0
   CONSTRAINT issue_pk PRIMARY KEY ,
   issue_type_ref_id             int default 0,
   issue_nbr                     int default 0,
   product_ref_id                int default 0 not null,
   issue_name                    varchar(255),
   issue_occur_dt                datetime default current_timestamp,
   issue_open_dt                 datetime default current_timestamp,
   version_ref_id                int default 0 not null,
   artifact_id                   int default 0 not null,
   test_log_id                   int default 0 not null,
   status_ref_id                 int default 0 not null,
   assigned_user_group_ref_id    int default 0 not null,
   assigned_user_group_id        int default 0,
   assigned_user_id              int default 0 not null,
   description                   text,
   priority_ref_id               int default 0 not null,
   severity_ref_id               int default 0 not null,
   fix_by_dt                     datetime,
   frequency_ref_id              int default 0,
   submitted_user_id             int default 0 not null,
   organization_id               int default 0,
   organization_environment_id   int default 0,
   reproduce_ref_id              int default 0 not null,
   parent_issue_id               int default 0,
   effort                        decimal default 0 not null,
   reproduce_directions          text,
   last_update                   text,
   history                       text,
   closed_dt                     datetime,
   closed_category_ref_id        int default 0,
   closed_user_id                int default 0,
   resolved_dt                   datetime,
   resolved_user_id              int default 0,
   resolved_category_ref_id      int default 0,
   external_alias1               varchar(255),
   external_alias2               varchar(255),
   external_alias3               varchar(50),
   client_os_ref_id              int default 0,
   server_os_ref_id              int default 0,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index issue_uk0 on issue (issue_id
);
create index issue_fk1 on issue (issue_occur_dt
);
create index issue_fk10 on issue (assigned_user_id
);
create index issue_fk11 on issue (organization_id
);
create index issue_fk12 on issue (issue_type_ref_id
);
create index issue_fk13 on issue (version_ref_id
);
create index issue_fk14 on issue (fix_by_dt
);
create index issue_fk15 on issue (parent_issue_id
);
create index issue_fk16 on issue (closed_dt
);
create index issue_fk17 on issue (external_alias1
);
create index issue_fk18 on issue (external_alias2
);
create index issue_fk19 on issue (external_alias3
);
create index issue_fk2 on issue (product_ref_id
);
create index issue_fk3 on issue (artifact_id
);
create index issue_fk4 on issue (test_log_id
);
create index issue_fk5 on issue (status_ref_id
);
create index issue_fk6 on issue (assigned_user_group_ref_id
);
create index issue_fk7 on issue (priority_ref_id
);
create index issue_fk8 on issue (severity_ref_id
);
create index issue_fk9 on issue (submitted_user_id
);
create index issue_uk1 on issue (issue_nbr, issue_type_ref_id
);
create table issue_log (
   issue_log_id                  int default 0
   CONSTRAINT issue_log_pk PRIMARY KEY ,
   issue_id                      int default 0 not null,
   action_dt                     datetime,
   action_text                   text not null,
   description                   text,
   action_user_id                int default 0 not null,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index issue_log_uk0 on issue_log (issue_log_id
);
create index issue_log_fk1 on issue_log (issue_id
);
create index issue_log_fk2 on issue_log (action_dt
);
create table record_file (
   record_file_id                int default 0
   CONSTRAINT record_file_pk PRIMARY KEY ,
   table_id                      int default 0 not null,
   table_ref_id                  int default 0 not null,
   file_type_ref_id              int default 0 not null,
   version                       decimal default 0 not null,
   source_file                   varchar(255),
   storage_file_name             varchar(200),
   storage_directory             varchar(255),
   file_name                     varchar(255),
   active_version_ind            int default 1 not null,
   description                   varchar(255),
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index record_file_uk0 on record_file (record_file_id
);
create index record_file_uk1 on record_file (table_id, table_ref_id, file_name, version
);
create index record_file_uk2 on record_file (storage_file_name, storage_directory, version
);
create table record_file_history (
   record_file_history_id        int default 0
   CONSTRAINT record_file_history_pk PRIMARY KEY ,
   record_file_id                int default 0 not null,
   history_dt                    datetime default current_timestamp,
   history_user_id               int default 0 not null,
   baseline_id                   int default 0 not null,
   table_id                      int default 0 not null,
   table_ref_id                  int default 0 not null,
   file_type_ref_id              int default 0 not null,
   version                       decimal default 0 not null,
   source_file                   varchar(255),
   storage_file_name             varchar(200),
   storage_directory             varchar(255),
   file_name                     varchar(255),
   active_version_ind            int default 1 not null,
   description                   varchar(255),
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index record_file_history_uk0 on record_file_history (record_file_history_id
);
create index record_file_history_uk1 on record_file_history (baseline_id, table_id, table_ref_id, file_name, version
);
create index record_file_history_uk2 on record_file_history (baseline_id, storage_file_name, storage_directory, version
);
create table record_parameter (
   record_parameter_id           int default 0
   CONSTRAINT record_parameter_pk PRIMARY KEY ,
   table_id                      int default 0 not null,
   table_ref_id                  int default 0 not null,
   parameter_name                varchar(255),
   model_column_ref_id           int default 0 not null,
   data_type_ref_id              int default 0 not null,
   parameter_seq                 int default 0 not null,
   application_control_id        int default 0 not null,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index record_parameter_uk0 on record_parameter (record_parameter_id
);
create unique index record_parameter_uk1 on record_parameter (table_id, table_ref_id
);
create table record_parameter_value (
   record_parameter_value_id     int default 0
   CONSTRAINT record_parameter_value_pk PRIMARY KEY ,
   record_parameter_id           int default 0,
   parameter_seq                 int default 0 not null,
   data_type_ref_id              int default 0 not null,
   value_int                     int,
   value_double                  decimal,
   value_string                  varchar(255),
   value_date                    datetime,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index record_parameter_value_uk0 on record_parameter_value (record_parameter_value_id
);
create index record_parameter_value_fk1 on record_parameter_value (record_parameter_id
);
create table reference (
   ref_id                        int default 0
   CONSTRAINT reference_pk PRIMARY KEY ,
   reference_group               varchar(50),
   app_type_ref_id               int default 0 not null,
   display_code                  varchar(64),
   display_sequence              int default 0 not null,
   display                       varchar(128),
   description                   varchar(255),
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index reference_uk0 on reference (ref_id
);
create unique index reference_uk1 on reference (reference_group, display_code, display_sequence, app_type_ref_id
);
create index reference_k1 on reference (reference_group, display
);
create index reference_k2 on reference (display_code, reference_group
);
create index reference_k3 on reference (reference_group, app_type_ref_id
);
create table reference_group (
   reference_group_id            int default 0
   CONSTRAINT reference_group_pk PRIMARY KEY ,
   reference_group               varchar(50),
   display                       varchar(50),
   description                   varchar(255),
   modification_ref_id           int default 324 not null,
   category_ref_id               int default 0 not null,
   unique_display_code_ind       int default 1 not null,
   framework_ind                 int default 0 not null,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index reference_group_uk0 on reference_group (reference_group_id
);
create unique index reference_group_uk1 on reference_group (reference_group
);
create table reference_history (
   reference_history_id          int default 0
   CONSTRAINT reference_history_pk PRIMARY KEY ,
   ref_id                        int default 0 not null,
   history_dt                    datetime default current_timestamp,
   history_user_id               int default 0 not null,
   baseline_id                   int default 0 not null,
   reference_group               varchar(50),
   app_type_ref_id               int default 0 not null,
   display_code                  varchar(64),
   display_sequence              int default 0 not null,
   display                       varchar(128),
   description                   varchar(255),
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index reference_history_uk0 on reference_history (reference_history_id
);
create index reference_history_k1 on reference_history (reference_group, display
);
create index reference_history_k2 on reference_history (display_code, reference_group
);
create index reference_history_k3 on reference_history (reference_group, app_type_ref_id
);
create index reference_history_uk1 on reference_history (baseline_id, reference_group, display_code, display_sequence, app_type_ref_id
);
create table reference_tree (
   reference_tree_id             int default 0
   CONSTRAINT reference_tree_pk PRIMARY KEY ,
   parent_table_key_id           int default 0 not null,
   parent_table_ref_id           int default 0 not null,
   relationship_ref_id           int default 0,
   table_key_id                  int default 0 not null,
   table_ref_id                  int default 0 not null,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index reference_tree_uk0 on reference_tree (reference_tree_id
);
create unique index reference_tree_uk1 on reference_tree (parent_table_key_id, parent_table_ref_id, table_key_id, table_ref_id
);
create table report (
   report_id                     int default 0
   CONSTRAINT report_pk PRIMARY KEY ,
   report_ref_id                 int default 0 not null,
   report_sql                    text,
   parameter_view_ref_id         int default 0,
   sql_parameter_script          text,
   xml_report_ind                int default 0,
   xml_select_script             text,
   xml_sort_script               text,
   xml_field_script              text,
   xml_image_qual                varchar(255),
   xml_xpath                     varchar(255),
   report_outline_script         text,
   report_outline_sql            text,
   outline_last_run_dt           datetime,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index report_uk0 on report (report_id
);
create unique index report_uk1 on report (report_ref_id
);
create index report_parameter_view_ref_id on report (parameter_view_ref_id
);
create table requirement_tree (
   requirement_tree_id           int default 0
   CONSTRAINT requirement_tree_pk PRIMARY KEY ,
   child_id                      int default 0 not null,
   child_artifact_ref_id         int default 0 not null,
   relation_ref_id               int default 0 not null,
   parent_id                     int default 0 not null,
   parent_artifact_ref_id        int default 0 not null,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index requirement_tree_uk0 on requirement_tree (requirement_tree_id
);
create unique index requirement_tree_uk1 on requirement_tree (parent_id, child_id, relation_ref_id
);
create unique index requirement_tree_uk2 on requirement_tree (child_id, parent_id, relation_ref_id
);
create index requirement_tree_k1 on requirement_tree (child_artifact_ref_id, child_id
);
create index requirement_tree_k2 on requirement_tree (parent_artifact_ref_id, parent_id
);
create index requirement_tree_k3 on requirement_tree (relation_ref_id
);
create table requirement_tree_history (
   requirement_tree_history_id   int default 0
   CONSTRAINT requirement_tree_history_pk PRIMARY KEY ,
   requirement_tree_id           int default 0 not null,
   history_dt                    datetime default current_timestamp,
   history_user_id               int default 0 not null,
   baseline_id                   int default 0 not null,
   child_id                      int default 0 not null,
   child_artifact_ref_id         int default 0 not null,
   relation_ref_id               int default 0 not null,
   parent_id                     int default 0 not null,
   parent_artifact_ref_id        int default 0 not null,
   create_dt                     datetime default current_timestamp,
   create_user_id                int default 0 not null,
   update_dt                     datetime default current_timestamp,
   update_user_id                int default 0 not null,
   update_count                  int default 0 not null,
   active_ind                    int default 1 not null,
   system_assigned_version_nbr   int default 5 not null,
   record_type_ref_id            int default 321 not null
);
create unique index requirement_tree_history_uk0 on requirement_tree_history (requirement_tree_history_id
);
create index requirement_tree_history_k1 on requirement_tree_history (child_artifact_ref_id, child_id
);
create index requirement_tree_history_k2 on requirement_tree_history (parent_artifact_ref_id, parent_id
);
create index requirement_tree_history_k3 on requirement_tree_history (relation_ref_id
);
create index requirement_tree_history_uk1 on requirement_tree_history (baseline_id, parent_id, child_id, relation_ref_id
);
create index requirement_tree_history_uk2 on requirement_tree_history (baseline_id, child_id, parent_id, relation_ref_id
);