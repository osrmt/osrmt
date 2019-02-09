drop view artifactdetail; 
create view artifactdetail as SELECT r1.DISPLAY as Product, 
r2.DISPLAY as ArtifactType, a.ARTIFACT_NAME as ArtifactName, 
 r5.DISPLAY as Version, r3.DISPLAY as Status, 
r4.DISPLAY as Priority,a.ARTIFACT_ID as ArtifactNbr, 
a.*, a.CREATE_DT as CreateDate 
FROM artifact a, reference r1, reference r2, 
reference r3, reference r4, reference r5, reference r6 
WHERE (((a.ACTIVE_IND)=1) And 
((a.PRODUCT_REF_ID)=r1.ref_id) And 
((a.ARTIFACT_REF_ID)=r2.ref_id) And 
((a.STATUS_REF_ID)=r3.ref_id) And 
 ((a.PRIORITY_REF_ID)=r4.ref_id) And 
 ((a.VERSION_REF_ID)=r5.ref_id) AND 
 a.ARTIFACT_LEVEL_REF_ID = r6.ref_id 
 and not r6.display_code='COMPONENT'); 
drop view artifactsummary; 
create view artifactsummary as SELECT r1.display as product, 
 r2.display as artifacttype, r3.display as status, 
 r4.display as priority, r5.display as version, a.* 
FROM artifact a, reference r1, reference r2, 
 reference r3, reference r4, reference r5 
WHERE a.active_ind=1 And a.product_ref_id=r1.ref_id 
And a.artifact_ref_id=r2.ref_id And a.status_ref_id=r3.ref_id 
 And a.priority_ref_id=r4.ref_id And a.version_ref_id = r5.ref_id; 
