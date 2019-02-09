drop view artifactdetail;
create view artifactdetail as SELECT r1.DISPLAY AS Product, r2.DISPLAY AS ArtifactType, a.ARTIFACT_NAME AS ArtifactName, r5.DISPLAY AS Version, r3.DISPLAY AS Status, r4.DISPLAY AS Priority,a.ARTIFACT_ID AS ArtifactNbr, a.*, a.CREATE_DT AS CreateDate
FROM artifact AS a, reference AS r1, reference AS r2, reference AS r3, reference AS r4, reference AS r5, reference AS r6
WHERE (((a.ACTIVE_IND)=1) And ((a.PRODUCT_REF_ID)=r1.ref_id) And ((a.ARTIFACT_REF_ID)=r2.ref_id) And ((a.STATUS_REF_ID)=r3.ref_id) And ((a.PRIORITY_REF_ID)=r4.ref_id) And ((a.VERSION_REF_ID)=r5.ref_id) AND a.ARTIFACT_LEVEL_REF_ID = r6.ref_id and not r6.display_code='COMPONENT');
drop view artifactsummary;
create view artifactsummary as SELECT r1.display AS product, r2.display AS artifacttype, r3.display AS status, r4.display AS priority, r5.display AS version, a.*
FROM artifact AS a, reference AS r1, reference AS r2, reference AS r3, reference AS r4, reference AS r5
WHERE a.active_ind=1 And a.product_ref_id=r1.ref_id And a.artifact_ref_id=r2.ref_id And a.status_ref_id=r3.ref_id And a.priority_ref_id=r4.ref_id And a.version_ref_id = r5.ref_id;
