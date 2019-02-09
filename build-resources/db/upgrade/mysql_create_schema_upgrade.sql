
create table app_control_template (
   app_control_template_id       int not null default 0,
   application_view_id           int not null default 0,
   display_sequence              int not null default 0,
   control_ref_id                int not null default 0,
   control_type_ref_id           int not null default 0,
   control_text                  varchar(100),
   control_description           varchar(255),
   model_column_ref_id           int not null default 0,
   application_custom_control_id int not null default 0,
   app_control_user_defined_id   int not null default 0,
   control_format                varchar(50),
   source_ref_id                 int not null default 0,
   default_value                 varchar(50),
   locked_ind                    int not null default 0,
   disabled_ind                  int not null default 0,
   required_ind                  int not null default 0,
   visible_ind                   int not null default 1,
   init_script                   varchar(255),
   focus_lost_script             longtext,
   focus_gained_script           longtext,
   image_path                    varchar(255),
   scrollpane_ind                int not null default 0,
   grow_height                   double not null default 0,
   grow_width                    int not null default 1,
   unit_width                    int not null default 1,
   unit_height                   int not null default 1,
   create_dt                     datetime,
   create_user_id                int not null default 0,
   update_dt                     datetime,
   update_user_id                int not null default 0,
   update_count                  int not null default 0,
   active_ind                    int not null default 1,
   system_assigned_version_nbr   int not null default 5,
   record_type_ref_id            int not null default 321
) default char set utf8;
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
   record_file_history_id        int default 0,
   record_file_id                int not null default 0,
   history_dt                    datetime,
   history_user_id               int not null default 0,
   baseline_id                   int not null default 0,
   table_id                      int not null default 0,
   table_ref_id                  int not null default 0,
   file_type_ref_id              int not null default 0,
   version                       double not null default 0,
   source_file                   varchar(255),
   storage_file_name             varchar(200),
   storage_directory             varchar(255),
   file_name                     varchar(255),
   active_version_ind            int not null default 1,
   description                   varchar(255),
   create_dt                     datetime,
   create_user_id                int not null default 0,
   update_dt                     datetime,
   update_user_id                int not null default 0,
   update_count                  int not null default 0,
   active_ind                    int not null default 1,
   system_assigned_version_nbr   int not null default 5,
   record_type_ref_id            int not null default 321
) default char set utf8;
create unique index record_file_history_pk on record_file_history (record_file_history_id
);
alter table record_file_history add constraint record_file_history_pk primary key (record_file_history_id);
create index record_file_history_uk1 on record_file_history (baseline_id, table_id, table_ref_id, file_name, version
);
create index record_file_history_uk2 on record_file_history (baseline_id, storage_file_name, storage_directory, version
);
create table reference_history (
   reference_history_id          int default 0,
   ref_id                        int not null default 0,
   history_dt                    datetime,
   history_user_id               int not null default 0,
   baseline_id                   int not null default 0,
   reference_group               varchar(50),
   app_type_ref_id               int not null default 0,
   display_code                  varchar(64),
   display_sequence              int not null default 0,
   display                       varchar(128),
   description                   varchar(255),
   create_dt                     datetime,
   create_user_id                int not null default 0,
   update_dt                     datetime,
   update_user_id                int not null default 0,
   update_count                  int not null default 0,
   active_ind                    int not null default 1,
   system_assigned_version_nbr   int not null default 5,
   record_type_ref_id            int not null default 321
) default char set utf8;
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
   requirement_tree_history_id   int default 0,
   requirement_tree_id           int not null default 0,
   history_dt                    datetime,
   history_user_id               int not null default 0,
   baseline_id                   int not null default 0,
   child_id                      int not null default 0,
   child_artifact_ref_id         int not null default 0,
   relation_ref_id               int not null default 0,
   parent_id                     int not null default 0,
   parent_artifact_ref_id        int not null default 0,
   create_dt                     datetime,
   create_user_id                int not null default 0,
   update_dt                     datetime,
   update_user_id                int not null default 0,
   update_count                  int not null default 0,
   active_ind                    int not null default 1,
   system_assigned_version_nbr   int not null default 5,
   record_type_ref_id            int not null default 321
) default char set utf8;
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