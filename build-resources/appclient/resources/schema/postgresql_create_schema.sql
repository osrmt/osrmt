
create table app_control_template (
   app_control_template_id       int8 not null default 0,
   application_view_id           int8 not null default 0,
   display_sequence              int8 not null default 0,
   control_ref_id                int8 not null default 0,
   control_type_ref_id           int8 not null default 0,
   control_text                  varchar(100),
   control_description           varchar(255),
   model_column_ref_id           int8 not null default 0,
   application_custom_control_id int8 not null default 0,
   app_control_user_defined_id   int8 not null default 0,
   control_format                varchar(50),
   source_ref_id                 int8 not null default 0,
   default_value                 varchar(50),
   locked_ind                    int8 not null default 0,
   disabled_ind                  int8 not null default 0,
   required_ind                  int8 not null default 0,
   visible_ind                   int8 not null default 1,
   init_script                   varchar(255),
   focus_lost_script             varchar,
   focus_gained_script           varchar,
   image_path                    varchar(255),
   scrollpane_ind                int8 not null default 0,
   grow_height                   float8 not null default 0,
   grow_width                    int8 not null default 1,
   unit_width                    int8 not null default 1,
   unit_height                   int8 not null default 1,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index app_control_template_pk on app_control_template (app_control_template_id
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
   application_control_id        int8 not null default 0,
   application_view_id           int8 not null default 0,
   display_sequence              int8 not null default 0,
   control_ref_id                int8 not null default 0,
   control_type_ref_id           int8 not null default 0,
   control_text                  varchar(100),
   control_description           varchar(255),
   model_column_ref_id           int8 not null default 0,
   application_custom_control_id int8 not null default 0,
   app_control_user_defined_id   int8 not null default 0,
   control_format                varchar(50),
   source_ref_id                 int8 not null default 0,
   default_value                 varchar(50),
   locked_ind                    int8 not null default 0,
   disabled_ind                  int8 not null default 0,
   required_ind                  int8 not null default 0,
   visible_ind                   int8 not null default 1,
   init_script                   varchar(255),
   focus_lost_script             varchar,
   focus_gained_script           varchar,
   image_path                    varchar(255),
   scrollpane_ind                int8 not null default 0,
   grow_height                   float8 not null default 0,
   grow_width                    int8 not null default 1,
   unit_width                    int8 not null default 1,
   unit_height                   int8 not null default 1,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index application_control_pk on application_control (application_control_id
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
   application_custom_control_id int8 default 0,
   custom_control_ref_id         int8 default 0,
   class_name                    varchar(255),
   populate_script               varchar,
   html_script                   varchar,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index application_custom_control_pk on application_custom_control (application_custom_control_id
);
create table application_security (
   application_security_id       int8 not null default 0,
   table_key_id                  int8 not null default 0,
   table_ref_id                  int8 not null default 0,
   application_view_id           int8 not null default 0,
   read_only_ind                 int8 not null default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index application_security_pk on application_security (application_security_id
);
create index application_security_fk3 on application_security (application_view_id
);
create index application_security_k1 on application_security (table_key_id, table_ref_id
);
create table application_setting (
   application_setting_id        int8 not null default 0,
   table_key_id                  int8 not null default 0,
   table_ref_id                  int8 not null default 0,
   application_view_id           int8 not null default 0,
   setting_ref_id                int8 not null default 0,
   data_type_ref_id              int8 not null default 0,
   value_int                     int8,
   value_double                  float8,
   value_string                  varchar,
   value_date                    timestamp,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index application_setting_pk on application_setting (application_setting_id
);
create index application_setting_fk1 on application_setting (application_view_id
);
create index application_setting_fk2 on application_setting (setting_ref_id
);
create table application_user (
   user_id                       int8 default 0,
   first_name                    varchar(50),
   last_name                     varchar(50),
   display_name                  varchar(50),
   user_login                    varchar(50) not null,
   password                      varchar(50),
   password_reset_ind            int8 not null default 0,
   email                         varchar(255),
   position_ref_id               int8 not null default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index application_user_pk on application_user (user_id
);
create unique index application_user_uk1 on application_user (user_login
);
create index application_user_fk1 on application_user (position_ref_id
);
create table application_user_group (
   application_user_group_id     int8 default 0,
   user_id                       int8 not null default 0,
   user_group_ref_id             int8 not null default 0,
   email                         varchar(255),
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index application_user_group_pk on application_user_group (application_user_group_id
);
create unique index application_user_group_uk1 on application_user_group (user_group_ref_id, user_id
);
create index application_user_group_fk1 on application_user_group (user_id
);
create table application_view (
   application_view_id           int8 not null default 0,
   application_ref_id            int8 not null default 0,
   app_type_ref_id               int8 not null default 0,
   view_ref_id                   int8 not null default 0,
   form_title                    varchar(50),
   init_script                   varchar,
   validation_script             varchar,
   form_width                    int8 default 0,
   form_height                   int8 default 0,
   form_center_screen_ind        int8 default 0,
   form_x_pos                    int8 default 0,
   form_y_pos                    int8 default 0,
   form_control_columns          int8 default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index application_view_pk on application_view (application_view_id
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
   artifact_id                   int8 default 0,
   product_ref_id                int8 not null default 0,
   version_ref_id                int8 not null default 0,
   artifact_ref_id               int8 not null default 0,
   artifact_nbr                  int8 not null default 0,
   artifact_seq                  int8 not null default 0,
   artifact_level_ref_id         int8 not null default 0,
   component_type_ref_id         int8 not null default 0,
   last_updated_baseline_id      int8 not null default 0,
   artifact_name                 varchar(255),
   description                   varchar,
   status_ref_id                 int8 not null default 0,
   priority_ref_id               int8 not null default 0,
   complexity_ref_id             int8 not null default 0,
   author_ref_id                 int8 not null default 0,
   assigned_ref_id               int8 not null default 0,
   category_ref_id               int8 not null default 0,
   assigned_user_id              int8 default 0,
   assigned_user_group_id        int8 default 0,
   effort                        float8 not null default 0,
   rationale                     varchar,
   origin                        varchar,
   goal                          varchar,
   context                       varchar,
   precondition                  varchar,
   postcondition                 varchar,
   summary                       varchar,
   external_references           varchar(255),
   planned_version_ref_id        int8 not null default 0,
   last_modified_version_ref_id  int8 not null default 0,
   removed_ind                   int8 not null default 0,
   module_ref_id                 int8 not null default 0,
   origin_category_ref_id        int8 not null default 0,
   change_reason                 varchar(255),
   change_made                   varchar,
   report_sequence               int8 default 0,
   report_outline                varchar(255),
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321,
   custom_text1                  varchar(255),
   custom_text2                  varchar(255),
   custom_text3                  varchar(255),
   custom_text4                  varchar(255),
   custom1_ref_id                int8 default 0,
   custom2_ref_id                int8 default 0,
   custom3_ref_id                int8 default 0,
   custom4_ref_id                int8 default 0,
   custom_date1                  timestamp,
   custom_date2                  timestamp,
   custom_memo1                  varchar,
   custom_memo2                  varchar,
   custom_int1                   int8 default 0,
   custom_int2                   int8 default 0,
   custom_double1                float8 default 0
) ;
create unique index artifact_pk on artifact (artifact_id
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
   artifact_document_id          int8 default 0,
   artifact_id                   int8 not null default 0,
   line_seq                      int8 default 0,
   line_text                     varchar,
   image_record_file_id          int8 default 0,
   style_ref_id                  int8 default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index artifact_document_pk on artifact_document (artifact_document_id
);
create unique index artifact_document_uk1 on artifact_document (artifact_id, line_seq
);
create table artifact_history (
   artifact_history_id           int8 not null default 0,
   artifact_id                   int8 not null default 0,
   history_dt                    timestamp default Now(),
   history_user_id               int8 not null default 0,
   baseline_id                   int8 not null default 0,
   product_ref_id                int8 not null default 0,
   version_ref_id                int8 not null default 0,
   artifact_ref_id               int8 not null default 0,
   artifact_nbr                  int8 not null default 0,
   artifact_seq                  int8 not null default 0,
   artifact_level_ref_id         int8 not null default 0,
   component_type_ref_id         int8 not null default 0,
   last_updated_baseline_id      int8 not null default 0,
   artifact_name                 varchar(255),
   description                   varchar,
   status_ref_id                 int8 not null default 0,
   priority_ref_id               int8 not null default 0,
   complexity_ref_id             int8 not null default 0,
   author_ref_id                 int8 not null default 0,
   assigned_ref_id               int8 not null default 0,
   category_ref_id               int8 not null default 0,
   assigned_user_id              int8 default 0,
   assigned_user_group_id        int8 default 0,
   effort                        float8 not null default 0,
   rationale                     varchar,
   origin                        varchar,
   goal                          varchar,
   context                       varchar,
   precondition                  varchar,
   postcondition                 varchar,
   summary                       varchar,
   external_references           varchar(255),
   planned_version_ref_id        int8 not null default 0,
   last_modified_version_ref_id  int8 not null default 0,
   removed_ind                   int8 not null default 0,
   module_ref_id                 int8 not null default 0,
   origin_category_ref_id        int8 not null default 0,
   change_reason                 varchar(255),
   change_made                   varchar,
   report_sequence               int8 default 0,
   report_outline                varchar(255),
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321,
   custom_text1                  varchar(255),
   custom_text2                  varchar(255),
   custom_text3                  varchar(255),
   custom_text4                  varchar(255),
   custom1_ref_id                int8 default 0,
   custom2_ref_id                int8 default 0,
   custom3_ref_id                int8 default 0,
   custom4_ref_id                int8 default 0,
   custom_date1                  timestamp,
   custom_date2                  timestamp,
   custom_memo1                  varchar,
   custom_memo2                  varchar,
   custom_int1                   int8 default 0,
   custom_int2                   int8 default 0,
   custom_double1                float8 default 0
) ;
create unique index artifact_history_pk on artifact_history (artifact_history_id
);
create index artifact_history_k1 on artifact_history (artifact_id
);
create index artifact_history_k2 on artifact_history (baseline_id
);
create table artifact_identity (
   artifact_identity_id          int8 default 0,
   source_ref_id                 int8 not null default 0,
   identifier                    varchar(255),
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index artifact_identity_pk on artifact_identity (artifact_identity_id
);
create table baseline (
   baseline_id                   int8 default 0,
   product_ref_id                int8 not null default 0,
   baseline_name                 varchar(255) not null,
   baseline_dt                   timestamp default Now(),
   description                   varchar(255),
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index baseline_pk on baseline (baseline_id
);
create unique index baseline_uk1 on baseline (product_ref_id, baseline_name
);
create index baseline_uk2 on baseline (baseline_dt, product_ref_id
);
create table issue (
   issue_id                      int8 default 0,
   issue_type_ref_id             int8 default 0,
   issue_nbr                     int8 default 0,
   product_ref_id                int8 not null default 0,
   issue_name                    varchar(255),
   issue_occur_dt                timestamp default Now(),
   issue_open_dt                 timestamp default Now(),
   version_ref_id                int8 not null default 0,
   artifact_id                   int8 not null default 0,
   test_log_id                   int8 not null default 0,
   status_ref_id                 int8 not null default 0,
   assigned_user_group_ref_id    int8 not null default 0,
   assigned_user_group_id        int8 default 0,
   assigned_user_id              int8 not null default 0,
   description                   varchar,
   priority_ref_id               int8 not null default 0,
   severity_ref_id               int8 not null default 0,
   fix_by_dt                     timestamp,
   frequency_ref_id              int8 default 0,
   submitted_user_id             int8 not null default 0,
   organization_id               int8 default 0,
   organization_environment_id   int8 default 0,
   reproduce_ref_id              int8 not null default 0,
   parent_issue_id               int8 default 0,
   effort                        float8 not null default 0,
   reproduce_directions          varchar,
   last_update                   varchar,
   history                       varchar,
   closed_dt                     timestamp,
   closed_category_ref_id        int8 default 0,
   closed_user_id                int8 default 0,
   resolved_dt                   timestamp,
   resolved_user_id              int8 default 0,
   resolved_category_ref_id      int8 default 0,
   external_alias1               varchar(255),
   external_alias2               varchar(255),
   external_alias3               varchar(50),
   client_os_ref_id              int8 default 0,
   server_os_ref_id              int8 default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index issue_pk on issue (issue_id
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
   issue_log_id                  int8 default 0,
   issue_id                      int8 not null default 0,
   action_dt                     timestamp,
   action_text                   varchar not null,
   description                   varchar,
   action_user_id                int8 not null default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index issue_log_pk on issue_log (issue_log_id
);
create index issue_log_fk1 on issue_log (issue_id
);
create index issue_log_fk2 on issue_log (action_dt
);
create table record_file (
   record_file_id                int8 default 0,
   table_id                      int8 not null default 0,
   table_ref_id                  int8 not null default 0,
   file_type_ref_id              int8 not null default 0,
   version                       float8 not null default 0,
   source_file                   varchar(255),
   storage_file_name             varchar(200),
   storage_directory             varchar(255),
   file_name                     varchar(255),
   active_version_ind            int8 not null default 1,
   description                   varchar(255),
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index record_file_pk on record_file (record_file_id
);
create index record_file_uk1 on record_file (table_id, table_ref_id, file_name, version
);
create index record_file_uk2 on record_file (storage_file_name, storage_directory, version
);
create table record_file_history (
   record_file_history_id        int8 default 0,
   record_file_id                int8 not null default 0,
   history_dt                    timestamp default Now(),
   history_user_id               int8 not null default 0,
   baseline_id                   int8 not null default 0,
   table_id                      int8 not null default 0,
   table_ref_id                  int8 not null default 0,
   file_type_ref_id              int8 not null default 0,
   version                       float8 not null default 0,
   source_file                   varchar(255),
   storage_file_name             varchar(200),
   storage_directory             varchar(255),
   file_name                     varchar(255),
   active_version_ind            int8 not null default 1,
   description                   varchar(255),
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index record_file_history_pk on record_file_history (record_file_history_id
);
create index record_file_history_uk1 on record_file_history (baseline_id, table_id, table_ref_id, file_name, version
);
create index record_file_history_uk2 on record_file_history (baseline_id, storage_file_name, storage_directory, version
);
create table record_parameter (
   record_parameter_id           int8 default 0,
   table_id                      int8 not null default 0,
   table_ref_id                  int8 not null default 0,
   parameter_name                varchar(255),
   model_column_ref_id           int8 not null default 0,
   data_type_ref_id              int8 not null default 0,
   parameter_seq                 int8 not null default 0,
   application_control_id        int8 not null default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index record_parameter_pk on record_parameter (record_parameter_id
);
create unique index record_parameter_uk1 on record_parameter (table_id, table_ref_id
);
create table record_parameter_value (
   record_parameter_value_id     int8 default 0,
   record_parameter_id           int8 default 0,
   parameter_seq                 int8 not null default 0,
   data_type_ref_id              int8 not null default 0,
   value_int                     int8,
   value_double                  float8,
   value_string                  varchar(255),
   value_date                    timestamp,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index record_parameter_value_pk on record_parameter_value (record_parameter_value_id
);
create index record_parameter_value_fk1 on record_parameter_value (record_parameter_id
);
create table reference (
   ref_id                        int8 default 0,
   reference_group               varchar(50),
   app_type_ref_id               int8 not null default 0,
   display_code                  varchar(64),
   display_sequence              int8 not null default 0,
   display                       varchar(128),
   description                   varchar(255),
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index reference_pk on reference (ref_id
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
   reference_group_id            int8 default 0,
   reference_group               varchar(50),
   display                       varchar(50),
   description                   varchar(255),
   modification_ref_id           int8 not null default 324,
   category_ref_id               int8 not null default 0,
   unique_display_code_ind       int8 not null default 1,
   framework_ind                 int8 not null default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index reference_group_pk on reference_group (reference_group_id
);
create unique index reference_group_uk1 on reference_group (reference_group
);
create table reference_history (
   reference_history_id          int8 default 0,
   ref_id                        int8 not null default 0,
   history_dt                    timestamp default Now(),
   history_user_id               int8 not null default 0,
   baseline_id                   int8 not null default 0,
   reference_group               varchar(50),
   app_type_ref_id               int8 not null default 0,
   display_code                  varchar(64),
   display_sequence              int8 not null default 0,
   display                       varchar(128),
   description                   varchar(255),
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index reference_history_pk on reference_history (reference_history_id
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
   reference_tree_id             int8 default 0,
   parent_table_key_id           int8 not null default 0,
   parent_table_ref_id           int8 not null default 0,
   relationship_ref_id           int8 default 0,
   table_key_id                  int8 not null default 0,
   table_ref_id                  int8 not null default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index reference_tree_pk on reference_tree (reference_tree_id
);
create unique index reference_tree_uk1 on reference_tree (parent_table_key_id, parent_table_ref_id, table_key_id, table_ref_id
);
create table report (
   report_id                     int8 default 0,
   report_ref_id                 int8 not null default 0,
   report_sql                    varchar,
   parameter_view_ref_id         int8 default 0,
   sql_parameter_script          varchar,
   xml_report_ind                int8 default 0,
   xml_select_script             varchar,
   xml_sort_script               varchar,
   xml_field_script              varchar,
   xml_image_qual                varchar(255),
   xml_xpath                     varchar(255),
   report_outline_script         varchar,
   report_outline_sql            varchar,
   outline_last_run_dt           timestamp,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index report_pk on report (report_id
);
create unique index report_uk1 on report (report_ref_id
);
create index report_parameter_view_ref_id on report (parameter_view_ref_id
);
create table requirement_tree (
   requirement_tree_id           int8 default 0,
   child_id                      int8 not null default 0,
   child_artifact_ref_id         int8 not null default 0,
   relation_ref_id               int8 not null default 0,
   parent_id                     int8 not null default 0,
   parent_artifact_ref_id        int8 not null default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index requirement_tree_pk on requirement_tree (requirement_tree_id
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
   requirement_tree_history_id   int8 default 0,
   requirement_tree_id           int8 not null default 0,
   history_dt                    timestamp default Now(),
   history_user_id               int8 not null default 0,
   baseline_id                   int8 not null default 0,
   child_id                      int8 not null default 0,
   child_artifact_ref_id         int8 not null default 0,
   relation_ref_id               int8 not null default 0,
   parent_id                     int8 not null default 0,
   parent_artifact_ref_id        int8 not null default 0,
   create_dt                     timestamp default Now(),
   create_user_id                int8 not null default 0,
   update_dt                     timestamp default Now(),
   update_user_id                int8 not null default 0,
   update_count                  int8 not null default 0,
   active_ind                    int8 not null default 1,
   system_assigned_version_nbr   int8 not null default 5,
   record_type_ref_id            int8 not null default 321
) ;
create unique index requirement_tree_history_pk on requirement_tree_history (requirement_tree_history_id
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