-- 코드를 입력하세요
SELECT fh.FLAVOR FROM ICECREAM_INFO ii 
    JOIN FIRST_HALF fh ON ii.FLAVOR = fh.FLAVOR
    WHERE fh.TOTAL_ORDER > 3000
        AND ii.INGREDIENT_TYPE = 'fruit_based'
    ORDER BY TOTAL_ORDER DESC;