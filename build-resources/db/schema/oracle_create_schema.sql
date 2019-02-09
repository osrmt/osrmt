
create table app_control_template (
   app_control_template_id       number default 0 not null,
   application_view_id           number default 0 not null,
   display_sequence              number default 0 not null,
   control_ref_id                number default 0 not null,
   control_type_ref_id           number default 0 not null,
   control_text                  varchar2(100),
   control_description           varchar2(255),
   model_column_ref_id           number default 0 not null,
   application_custom_control_id number default 0 not null,
   app_control_user_defined_id   number default 0 not null,
   control_format                varchar2(50),
   source_ref_id                 number default 0 not null,
   default_value                 varchar2(50),
   locked_ind                    number default 0 not null,
   disabled_ind                  number default 0 not null,
   required_ind                  number default 0 not null,
   visible_ind                   number default 1 not null,
   init_script                   varchar2(255),
   focus_lost_script             clob,
   focus_gained_script           clob,
   image_path                    varchar2(255),
   scrollpane_ind                number default 0 not null,
   grow_height                   number default 0 not null,
   grow_width                    number default 1 not null,
   unit_width                    number default 1 not null,
   unit_height                   number default 1 not null,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index app_control_template_pk on app_control_template (app_control_template_id
);
alter table app_control_template add constraint app_control_template_pk primary key (app_control_template_id);
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
   application_control_id        number default 0 not null,
   application_view_id           number default 0 not null,
   display_sequence              number default 0 not null,
   control_ref_id                number default 0 not null,
   control_type_ref_id           number default 0 not null,
   control_text                  varchar2(100),
   control_description           varchar2(255),
   model_column_ref_id           number default 0 not null,
   application_custom_control_id number default 0 not null,
   app_control_user_defined_id   number default 0 not null,
   control_format                varchar2(50),
   source_ref_id                 number default 0 not null,
   default_value                 varchar2(50),
   locked_ind                    number default 0 not null,
   disabled_ind                  number default 0 not null,
   required_ind                  number default 0 not null,
   visible_ind                   number default 1 not null,
   init_script                   varchar2(255),
   focus_lost_script             clob,
   focus_gained_script           clob,
   image_path                    varchar2(255),
   scrollpane_ind                number default 0 not null,
   grow_height                   number default 0 not null,
   grow_width                    number default 1 not null,
   unit_width                    number default 1 not null,
   unit_height                   number default 1 not null,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index application_control_pk on application_control (application_control_id
);
alter table application_control add constraint application_control_pk primary key (application_control_id);
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
   application_custom_control_id number default 0,
   custom_control_ref_id         number default 0,
   class_name                    varchar2(255),
   populate_script               clob,
   html_script                   clob,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index application_custom_control_pk on application_custom_control (application_custom_control_id
);
alter table application_custom_control add constraint application_custom_control_pk primary key (application_custom_control_id);
create table application_security (
   application_security_id       number default 0 not null,
   table_key_id                  number default 0 not null,
   table_ref_id                  number default 0 not null,
   application_view_id           number default 0 not null,
   read_only_ind                 number default 0 not null,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index application_security_pk on application_security (application_security_id
);
alter table application_security add constraint application_security_pk primary key (application_security_id);
create index application_security_fk3 on application_security (application_view_id
);
create index application_security_k1 on application_security (table_key_id, table_ref_id
);
create table application_setting (
   application_setting_id        number default 0 not null,
   table_key_id                  number default 0 not null,
   table_ref_id                  number default 0 not null,
   application_view_id           number default 0 not null,
   setting_ref_id                number default 0 not null,
   data_type_ref_id              number default 0 not null,
   value_int                     number,
   value_double                  number,
   value_string                  clob,
   value_date                    date,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index application_setting_pk on application_setting (application_setting_id
);
alter table application_setting add constraint application_setting_pk primary key (application_setting_id);
create index application_setting_fk1 on application_setting (application_view_id
);
create index application_setting_fk2 on application_setting (setting_ref_id
);
create table application_user (
   user_id                       number default 0,
   first_name                    varchar2(50),
   last_name                     varchar2(50),
   display_name                  varchar2(50),
   user_login                    varchar2(50) not null,
   password                      varchar2(50),
   password_reset_ind            number default 0 not null,
   email                         varchar2(255),
   position_ref_id               number default 0 not null,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index application_user_pk on application_user (user_id
);
alter table application_user add constraint application_user_pk primary key (user_id);
create unique index application_user_uk1 on application_user (user_login
);
create index application_user_fk1 on application_user (position_ref_id
);
create table application_user_group (
   application_user_group_id     number default 0,
   user_id                       number default 0 not null,
   user_group_ref_id             number default 0 not null,
   email                         varchar2(255),
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index application_user_group_pk on application_user_group (application_user_group_id
);
alter table application_user_group add constraint application_user_group_pk primary key (application_user_group_id);
create unique index application_user_group_uk1 on application_user_group (user_group_ref_id, user_id
);
create index application_user_group_fk1 on application_user_group (user_id
);
create table application_view (
   application_view_id           number default 0 not null,
   application_ref_id            number default 0 not null,
   app_type_ref_id               number default 0 not null,
   view_ref_id                   number default 0 not null,
   form_title                    varchar2(50),
   init_script                   clob,
   validation_script             clob,
   form_width                    number default 0,
   form_height                   number default 0,
   form_center_screen_ind        number default 0,
   form_x_pos                    number default 0,
   form_y_pos                    number default 0,
   form_control_columns          number default 0,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index application_view_pk on application_view (application_view_id
);
alter table application_view add constraint application_view_pk primary key (application_view_id);
create unique index application_view_uk1 on application_view (application_ref_id, app_type_ref_id, view_ref_id
);
create index application_view_fk1 on application_view (app_type_ref_id
);
create index application_view_fk2 on application_view (application_ref_id
);
create index application_view_fk3 on application_view (view_ref_id
);
create table artifact (
   artifact_id                   number default 0,
   product_ref_id                number default 0 not null,
   version_ref_id                number default 0 not null,
   artifact_ref_id               number default 0 not null,
   artifact_nbr                  number default 0 not null,
   artifact_seq                  number default 0 not null,
   artifact_level_ref_id         number default 0 not null,
   component_type_ref_id         number default 0 not null,
   last_updated_baseline_id      number default 0 not null,
   artifact_name                 varchar2(255),
   description                   clob,
   status_ref_id                 number default 0 not null,
   priority_ref_id               number default 0 not null,
   complexity_ref_id             number default 0 not null,
   author_ref_id                 number default 0 not null,
   assigned_ref_id               number default 0 not null,
   category_ref_id               number default 0 not null,
   assigned_user_id              number default 0,
   assigned_user_group_id        number default 0,
   effort                        number default 0 not null,
   rationale                     clob,
   origin                        clob,
   goal                          clob,
   context                       clob,
   precondition                  clob,
   postcondition                 clob,
   summary                       clob,
   external_references           varchar2(255),
   planned_version_ref_id        number default 0 not null,
   last_modified_version_ref_id  number default 0 not null,
   removed_ind                   number default 0 not null,
   module_ref_id                 number default 0 not null,
   origin_category_ref_id        number default 0 not null,
   change_reason                 varchar2(255),
   change_made                   clob,
   report_sequence               number default 0,
   report_outline                varchar2(255),
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null,
   custom_text1                  varchar2(255),
   custom_text2                  varchar2(255),
   custom_text3                  varchar2(255),
   custom_text4                  varchar2(255),
   custom1_ref_id                number default 0,
   custom2_ref_id                number default 0,
   custom3_ref_id                number default 0,
   custom4_ref_id                number default 0,
   custom_date1                  date,
   custom_date2                  date,
   custom_memo1                  clob,
   custom_memo2                  clob,
   custom_int1                   number default 0,
   custom_int2                   number default 0,
   custom_double1                number default 0
);
create unique index artifact_pk on artifact (artifact_id
);
alter table artifact add constraint artifact_pk primary key (artifact_id);
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
   artifact_document_id          number default 0,
   artifact_id                   number default 0 not null,
   line_seq                      number default 0,
   line_text                     clob,
   image_record_file_id          number default 0,
   style_ref_id                  number default 0,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index artifact_document_pk on artifact_document (artifact_document_id
);
alter table artifact_document add constraint artifact_document_pk primary key (artifact_document_id);
create unique index artifact_document_uk1 on artifact_document (artifact_id, line_seq
);
create table artifact_history (
   artifact_history_id           number default 0 not null,
   artifact_id                   number default 0 not null,
   history_dt                    date default sysdate,
   history_user_id               number default 0 not null,
   baseline_id                   number default 0 not null,
   product_ref_id                number default 0 not null,
   version_ref_id                number default 0 not null,
   artifact_ref_id               number default 0 not null,
   artifact_nbr                  number default 0 not null,
   artifact_seq                  number default 0 not null,
   artifact_level_ref_id         number default 0 not null,
   component_type_ref_id         number default 0 not null,
   last_updated_baseline_id      number default 0 not null,
   artifact_name                 varchar2(255),
   description                   clob,
   status_ref_id                 number default 0 not null,
   priority_ref_id               number default 0 not null,
   complexity_ref_id             number default 0 not null,
   author_ref_id                 number default 0 not null,
   assigned_ref_id               number default 0 not null,
   category_ref_id               number default 0 not null,
   assigned_user_id              number default 0,
   assigned_user_group_id        number default 0,
   effort                        number default 0 not null,
   rationale                     clob,
   origin                        clob,
   goal                          clob,
   context                       clob,
   precondition                  clob,
   postcondition                 clob,
   summary                       clob,
   external_references           varchar2(255),
   planned_version_ref_id        number default 0 not null,
   last_modified_version_ref_id  number default 0 not null,
   removed_ind                   number default 0 not null,
   module_ref_id                 number default 0 not null,
   origin_category_ref_id        number default 0 not null,
   change_reason                 varchar2(255),
   change_made                   clob,
   report_sequence               number default 0,
   report_outline                varchar2(255),
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null,
   custom_text1                  varchar2(255),
   custom_text2                  varchar2(255),
   custom_text3                  varchar2(255),
   custom_text4                  varchar2(255),
   custom1_ref_id                number default 0,
   custom2_ref_id                number default 0,
   custom3_ref_id                number default 0,
   custom4_ref_id                number default 0,
   custom_date1                  date,
   custom_date2                  date,
   custom_memo1                  clob,
   custom_memo2                  clob,
   custom_int1                   number default 0,
   custom_int2                   number default 0,
   custom_double1                number default 0
);
create unique index artifact_history_pk on artifact_history (artifact_history_id
);
alter table artifact_history add constraint artifact_history_pk primary key (artifact_history_id);
create index artifact_history_k1 on artifact_history (artifact_id
);
create index artifact_history_k2 on artifact_history (baseline_id
);
create table artifact_identity (
   artifact_identity_id          number default 0,
   source_ref_id                 number default 0 not null,
   identifier                    varchar2(255),
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index artifact_identity_pk on artifact_identity (artifact_identity_id
);
alter table artifact_identity add constraint artifact_identity_pk primary key (artifact_identity_id);
create table baseline (
   baseline_id                   number default 0,
   product_ref_id                number default 0 not null,
   baseline_name                 varchar2(255) not null,
   baseline_dt                   date default sysdate,
   description                   varchar2(255),
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index baseline_pk on baseline (baseline_id
);
alter table baseline add constraint baseline_pk primary key (baseline_id);
create unique index baseline_uk1 on baseline (product_ref_id, baseline_name
);
create index baseline_uk2 on baseline (baseline_dt, product_ref_id
);
create table issue (
   issue_id                      number default 0,
   issue_type_ref_id             number default 0,
   issue_nbr                     number default 0,
   product_ref_id                number default 0 not null,
   issue_name                    varchar2(255),
   issue_occur_dt                date default sysdate,
   issue_open_dt                 date default sysdate,
   version_ref_id                number default 0 not null,
   artifact_id                   number default 0 not null,
   test_log_id                   number default 0 not null,
   status_ref_id                 number default 0 not null,
   assigned_user_group_ref_id    number default 0 not null,
   assigned_user_group_id        number default 0,
   assigned_user_id              number default 0 not null,
   description                   clob,
   priority_ref_id               number default 0 not null,
   severity_ref_id               number default 0 not null,
   fix_by_dt                     date,
   frequency_ref_id              number default 0,
   submitted_user_id             number default 0 not null,
   organization_id               number default 0,
   organization_environment_id   number default 0,
   reproduce_ref_id              number default 0 not null,
   parent_issue_id               number default 0,
   effort                        number default 0 not null,
   reproduce_directions          clob,
   last_update                   clob,
   history                       clob,
   closed_dt                     date,
   closed_category_ref_id        number default 0,
   closed_user_id                number default 0,
   resolved_dt                   date,
   resolved_user_id              number default 0,
   resolved_category_ref_id      number default 0,
   external_alias1               varchar2(255),
   external_alias2               varchar2(255),
   external_alias3               varchar2(50),
   client_os_ref_id              number default 0,
   server_os_ref_id              number default 0,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index issue_pk on issue (issue_id
);
alter table issue add constraint issue_pk primary key (issue_id);
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
   issue_log_id                  number default 0,
   issue_id                      number default 0 not null,
   action_dt                     date,
   action_text                   clob not null,
   description                   clob,
   action_user_id                number default 0 not null,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index issue_log_pk on issue_log (issue_log_id
);
alter table issue_log add constraint issue_log_pk primary key (issue_log_id);
create index issue_log_fk1 on issue_log (issue_id
);
create index issue_log_fk2 on issue_log (action_dt
);
create table record_file (
   record_file_id                number default 0,
   table_id                      number default 0 not null,
   table_ref_id                  number default 0 not null,
   file_type_ref_id              number default 0 not null,
   version                       number default 0 not null,
   source_file                   varchar2(255),
   storage_file_name             varchar2(200),
   storage_directory             varchar2(255),
   file_name                     varchar2(255),
   active_version_ind            number default 1 not null,
   description                   varchar2(255),
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index record_file_pk on record_file (record_file_id
);
alter table record_file add constraint record_file_pk primary key (record_file_id);
create index record_file_uk1 on record_file (table_id, table_ref_id, file_name, version
);
create index record_file_uk2 on record_file (storage_file_name, storage_directory, version
);
create table record_file_history (
   record_file_history_id        number default 0,
   record_file_id                number default 0 not null,
   history_dt                    date default sysdate,
   history_user_id               number default 0 not null,
   baseline_id                   number default 0 not null,
   table_id                      number default 0 not null,
   table_ref_id                  number default 0 not null,
   file_type_ref_id              number default 0 not null,
   version                       number default 0 not null,
   source_file                   varchar2(255),
   storage_file_name             varchar2(200),
   storage_directory             varchar2(255),
   file_name                     varchar2(255),
   active_version_ind            number default 1 not null,
   description                   varchar2(255),
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index record_file_history_pk on record_file_history (record_file_history_id
);
alter table record_file_history add constraint record_file_history_pk primary key (record_file_history_id);
create index record_file_history_uk1 on record_file_history (baseline_id, table_id, table_ref_id, file_name, version
);
create index record_file_history_uk2 on record_file_history (baseline_id, storage_file_name, storage_directory, version
);
create table record_parameter (
   record_parameter_id           number default 0,
   table_id                      number default 0 not null,
   table_ref_id                  number default 0 not null,
   parameter_name                varchar2(255),
   model_column_ref_id           number default 0 not null,
   data_type_ref_id              number default 0 not null,
   parameter_seq                 number default 0 not null,
   application_control_id        number default 0 not null,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index record_parameter_pk on record_parameter (record_parameter_id
);
alter table record_parameter add constraint record_parameter_pk primary key (record_parameter_id);
create unique index record_parameter_uk1 on record_parameter (table_id, table_ref_id
);
create table record_parameter_value (
   record_parameter_value_id     number default 0,
   record_parameter_id           number default 0,
   parameter_seq                 number default 0 not null,
   data_type_ref_id              number default 0 not null,
   value_int                     number,
   value_double                  number,
   value_string                  varchar2(255),
   value_date                    date,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index record_parameter_value_pk on record_parameter_value (record_parameter_value_id
);
alter table record_parameter_value add constraint record_parameter_value_pk primary key (record_parameter_value_id);
create index record_parameter_value_fk1 on record_parameter_value (record_parameter_id
);
create table reference (
   ref_id                        number default 0,
   reference_group               varchar2(50),
   app_type_ref_id               number default 0 not null,
   display_code                  varchar2(64),
   display_sequence              number default 0 not null,
   display                       varchar2(128),
   description                   varchar2(255),
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index reference_pk on reference (ref_id
);
alter table reference add constraint reference_pk primary key (ref_id);
create unique index reference_uk1 on reference (reference_group, display_code, display_sequence, app_type_ref_id
);
create index reference_k1 on reference (reference_group, display
);
create index reference_k2 on reference (display_code, reference_group
);
create index reference_k3 on reference (reference_group, app_type_ref_id
);
create table reference_group (
   reference_group_id            number default 0,
   reference_group               varchar2(50),
   display                       varchar2(50),
   description                   varchar2(255),
   modification_ref_id           number default 324 not null,
   category_ref_id               number default 0 not null,
   unique_display_code_ind       number default 1 not null,
   framework_ind                 number default 0 not null,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index reference_group_pk on reference_group (reference_group_id
);
alter table reference_group add constraint reference_group_pk primary key (reference_group_id);
create unique index reference_group_uk1 on reference_group (reference_group
);
create table reference_history (
   reference_history_id          number default 0,
   ref_id                        number default 0 not null,
   history_dt                    date default sysdate,
   history_user_id               number default 0 not null,
   baseline_id                   number default 0 not null,
   reference_group               varchar2(50),
   app_type_ref_id               number default 0 not null,
   display_code                  varchar2(64),
   display_sequence              number default 0 not null,
   display                       varchar2(128),
   description                   varchar2(255),
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index reference_history_pk on reference_history (reference_history_id
);
alter table reference_history add constraint reference_history_pk primary key (reference_history_id);
create index reference_history_k1 on reference_history (reference_group, display
);
create index reference_history_k2 on reference_history (display_code, reference_group
);
create index reference_history_k3 on reference_history (reference_group, app_type_ref_id
);
create index reference_history_uk1 on reference_history (baseline_id, reference_group, display_code, display_sequence, app_type_ref_id
);
create table reference_tree (
   reference_tree_id             number default 0,
   parent_table_key_id           number default 0 not null,
   parent_table_ref_id           number default 0 not null,
   relationship_ref_id           number default 0,
   table_key_id                  number default 0 not null,
   table_ref_id                  number default 0 not null,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index reference_tree_pk on reference_tree (reference_tree_id
);
alter table reference_tree add constraint reference_tree_pk primary key (reference_tree_id);
create unique index reference_tree_uk1 on reference_tree (parent_table_key_id, parent_table_ref_id, table_key_id, table_ref_id
);
create table report (
   report_id                     number default 0,
   report_ref_id                 number default 0 not null,
   report_sql                    clob,
   parameter_view_ref_id         number default 0,
   sql_parameter_script          clob,
   xml_report_ind                number default 0,
   xml_select_script             clob,
   xml_sort_script               clob,
   xml_field_script              clob,
   xml_image_qual                varchar2(255),
   xml_xpath                     varchar2(255),
   report_outline_script         clob,
   report_outline_sql            clob,
   outline_last_run_dt           date,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index report_pk on report (report_id
);
alter table report add constraint report_pk primary key (report_id);
create unique index report_uk1 on report (report_ref_id
);
create index report_parameter_view_ref_id on report (parameter_view_ref_id
);
create table requirement_tree (
   requirement_tree_id           number default 0,
   child_id                      number default 0 not null,
   child_artifact_ref_id         number default 0 not null,
   relation_ref_id               number default 0 not null,
   parent_id                     number default 0 not null,
   parent_artifact_ref_id        number default 0 not null,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index requirement_tree_pk on requirement_tree (requirement_tree_id
);
alter table requirement_tree add constraint requirement_tree_pk primary key (requirement_tree_id);
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
   requirement_tree_history_id   number default 0,
   requirement_tree_id           number default 0 not null,
   history_dt                    date default sysdate,
   history_user_id               number default 0 not null,
   baseline_id                   number default 0 not null,
   child_id                      number default 0 not null,
   child_artifact_ref_id         number default 0 not null,
   relation_ref_id               number default 0 not null,
   parent_id                     number default 0 not null,
   parent_artifact_ref_id        number default 0 not null,
   create_dt                     date default sysdate,
   create_user_id                number default 0 not null,
   update_dt                     date default sysdate,
   update_user_id                number default 0 not null,
   update_count                  number default 0 not null,
   active_ind                    number default 1 not null,
   system_assigned_version_nbr   number default 5 not null,
   record_type_ref_id            number default 321 not null
);
create unique index requirement_tree_history_pk on requirement_tree_history (requirement_tree_history_id
);
alter table requirement_tree_history add constraint requirement_tree_history_pk primary key (requirement_tree_history_id);
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