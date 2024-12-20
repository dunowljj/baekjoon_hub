SELECT e.ID, IFNULL(ec.CHILD_COUNT, 0) AS CHILD_COUNT 
FROM ECOLI_DATA e 
LEFT JOIN (SELECT PARENT_ID AS ID, count(*) AS CHILD_COUNT
           FROM ECOLI_DATA
           GROUP BY PARENT_ID) AS ec ON e.ID = ec.ID