import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import test.generated.Tables;
import test.generated.tables.Author;

import static test.generated.Tables.*;
import static org.jooq.impl.DSL.*;

import java.sql.*;

/**
 * JOOQ快速手册－－简单7步骤
 * https://www.jianshu.com/p/e617c30ba108
 *
 */
public class Main
{

    public static void main(String[] args)
    {
        String userName = "root";
        String password = "root123";
        String url = "jdbc:mysql://localhost:3306/library";

        // Connection is the only JDBC resource that we need 
        // PreparedStatement and ResultSet are handled by jOOQ, internally 
        try (Connection conn = DriverManager.getConnection(url, userName, password))
        {
            //连接：我们添加一个用jOOQ的查询DSL构造的简单查询:
            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            Result<Record> result = create.select().from(AUTHOR).fetch();


            //遍历：在查询出结果的行之后，让我们迭代结果并打印出数据:
            for (Record r : result)
            {
                Integer id = r.getValue(AUTHOR.ID);
                String firstName = r.getValue(AUTHOR.FIRST_NAME);
                String lastName = r.getValue(AUTHOR.LAST_NAME);

                System.out.println("ID: " + id + " first name: " + firstName + " last name: " + lastName);
            }

            //插入
            create.insertInto(Tables.AUTHOR)
                    .set(AUTHOR.ID, 3)
                    .set(AUTHOR.FIRST_NAME,"newname")
                    .set(AUTHOR.LAST_NAME, "new lastname")
                    .execute();

        }
        // For the sake of this tutorial, let's keep exception handling simple 
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}