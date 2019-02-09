alter table artifact add (
   assigned_user_id              int default 0,
   assigned_user_group_id        int default 0,
   change_reason                 varchar(255),
   change_made                   longtext,
   report_sequence               int default 0,
   report_outline                varchar(255),
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
   custom_memo1                  longtext,
   custom_memo2                  longtext,
   custom_int1                   int default 0,
   custom_int2                   int default 0,
   custom_double1                int default 0
);
alter table artifact_history add (
   assigned_user_id              int default 0,
   assigned_user_group_id        int default 0,
   change_reason                 varchar(255),
   change_made                   longtext,
   report_sequence               int default 0,
   report_outline                varchar(255),
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
   custom_memo1                  longtext,
   custom_memo2                  longtext,
   custom_int1                   int default 0,
   custom_int2                   int default 0,
   custom_double1                int default 0
);
alter table issue add (
   assigned_user_group_id        int default 0
);
alter table report add (
   parameter_view_ref_id         int default 0,
   report_outline_script         longtext,
   report_outline_sql            longtext,
   outline_last_run_dt           datetime
);
