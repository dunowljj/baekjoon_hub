SELECT * FROM PLACES 
    WHERE HOST_ID IN (
            SELECT HOST_ID FROM PLACES
                GROUP BY HOST_ID
                    HAVING count(*) >= 2
                    )
                