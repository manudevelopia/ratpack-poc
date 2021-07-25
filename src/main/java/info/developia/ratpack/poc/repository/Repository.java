package info.developia.ratpack.poc.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

abstract class Repository<T> {
    private final Class<T> typeParameterClass = getTypeParameterClass();
    private static SqlSessionFactory sqlSessionFactory;

    Repository() {
        sqlSessionFactory = getSession(typeParameterClass.getPackageName());
    }

    @SuppressWarnings("unchecked")
    private Class<T> getTypeParameterClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    <K> K repository(Function<T, K> name) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            K result = name.apply(session.getMapper(typeParameterClass));
            session.commit();
            return result;
        } catch (Exception e) {
            throw new PersistenceException(e.getMessage());
        }
    }

    private static SqlSessionFactory buildSqlSessionFactory(String mappersPackageName) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        DataSource dataSource = getHikariDataSource();

        Environment environment = new Environment("Default", new JdbcTransactionFactory(), dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers(mappersPackageName);

        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private static HikariDataSource getHikariDataSource() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(dotenv.get("DATABASE_URL_CONN"));
        config.setUsername(dotenv.get("DATABASE_USERNAME"));
        config.setPassword(dotenv.get("DATABASE_PASSWORD"));
        config.setAutoCommit(false);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(4);
        config.setConnectionTestQuery("SELECT 1");
        return new HikariDataSource(config);
    }

    static SqlSessionFactory getSession(String mappersPackageName) {
        if (sqlSessionFactory == null) {
            sqlSessionFactory = buildSqlSessionFactory(mappersPackageName);
        }
        return sqlSessionFactory;
    }
}
