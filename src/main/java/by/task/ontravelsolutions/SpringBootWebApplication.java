package by.task.ontravelsolutions;

@SpringBootApplication
public class SpringBootWebApplication extends SpringBootServletInitializer {

    /**
     * Метод выполняет конфигурацию Spring-приложения
     * @param application
     * */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootWebApplication.class);
    }

    /**
     * Главный метод выполняет запуск Spring-приложения и активацию бота
     * */
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }
}
