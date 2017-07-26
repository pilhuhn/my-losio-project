package io.openshift.booster;

import com.uber.jaeger.Configuration;
import com.uber.jaeger.samplers.ProbabilisticSampler;
import io.opentracing.util.GlobalTracer;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import io.opentracing.Tracer;

@WebListener
public class TracerSetupListener
		implements
			javax.servlet.ServletContextListener {

	@Inject
	Tracer tracer;

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}

	@Override
	public void contextInitialized(
			javax.servlet.ServletContextEvent servletContextEvent) {
		GlobalTracer.register(tracer);
	}

	@Produces
	@Singleton
	static Tracer jaegerTracer() {
		return new Configuration("wildfly-swarm",
				new Configuration.SamplerConfiguration(
						ProbabilisticSampler.TYPE, 1),
				new Configuration.ReporterConfiguration()).getTracer();
	}
}