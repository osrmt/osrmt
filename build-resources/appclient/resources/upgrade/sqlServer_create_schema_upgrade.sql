
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