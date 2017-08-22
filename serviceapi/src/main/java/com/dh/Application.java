package com.dh;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

/*{@Configuration
@ComponentScan
@EnableAutoConfiguration}==@SpringBootApplication*/
@SpringBootApplication
public class Application {

    /**
     * 该方法解决了spring-boot 文件上传大小限制问题。
     *
     * @return 还未测试
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("300KB");
        factory.setMaxRequestSize("300KB");
        return factory.createMultipartConfig();
    }

    /**
     * 该方法解决了spring-boot-1.3版本 post请求参数大小限制问题
     * 该问题在 1.4.0.M2 版本中添加新的属性 server.max-http-post-size 配置即可
     *
     * @return 测试已通过
     */
    @Bean
    public EmbeddedServletContainerCustomizer jettyCustomizer() {
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof JettyEmbeddedServletContainerFactory) {
                    ((JettyEmbeddedServletContainerFactory) container)
                            .addServerCustomizers(new JettyServerCustomizer() {

                                @Override
                                public void customize(Server server) {
                                    setHandlerMaxHttpPostSize(200 * 1024 * 1024, server.getHandlers());
                                }

                                private void setHandlerMaxHttpPostSize(int maxHttpPostSize, Handler... handlers) {
                                    for (Handler handler : handlers) {
                                        if (handler instanceof ContextHandler) {
                                            ((ContextHandler) handler).setMaxFormContentSize(maxHttpPostSize);
                                        } else if (handler instanceof HandlerWrapper) {
                                            setHandlerMaxHttpPostSize(maxHttpPostSize,
                                                    ((HandlerWrapper) handler).getHandler());
                                        } else if (handler instanceof HandlerCollection) {
                                            setHandlerMaxHttpPostSize(maxHttpPostSize,
                                                    ((HandlerCollection) handler).getHandlers());
                                        }
                                    }
                                }
                            });
                }
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
