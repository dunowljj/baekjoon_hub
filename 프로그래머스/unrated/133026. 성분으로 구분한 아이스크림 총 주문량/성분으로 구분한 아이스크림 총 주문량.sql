-- 코드를 입력하세요
SELECT i.INGREDIENT_TYPE, sum(TOTAL_ORDER) AS TOTAL_ORDER
    FROM FIRST_HALF f 
    JOIN ICECREAM_INFO i ON f.FLAVOR = i.FLAVOR
    
    GROUP BY i.INGREDIENT_TYPE;