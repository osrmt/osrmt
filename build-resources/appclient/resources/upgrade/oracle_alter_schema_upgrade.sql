alter table artifact add (
   assigned_user_id              number default 0,
   assigned_user_group_id        number default 0,
   change_reason                 varchar2(255),
   change_made                   clob,
   report_sequence               number default 0,
   report_outline                varchar2(255),
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
alter table artifact_history add (
   assigned_user_id              number default 0,
   assigned_user_group_id        number default 0,
   change_reason                 varchar2(255),
   change_made                   clob
   report_sequence               number default 0,
   report_outline                varchar2(255),
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
alter table issue add (
   assigned_user_group_id        number default 0
);
alter table report add (
   parameter_view_ref_id         number default 0,
   report_outline_script         clob,
   report_outline_sql            clob,
   outline_last_run_dt           date
);
