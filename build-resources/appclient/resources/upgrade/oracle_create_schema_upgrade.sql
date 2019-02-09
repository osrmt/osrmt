
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