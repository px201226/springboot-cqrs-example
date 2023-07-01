package com.blogsearch.blogcore.springboot.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

@Configuration
@RequiredArgsConstructor
public class EmbeddedRedisConfig {

	private final RedisProperties redisProperties;
	private RedisServer redisServer;

	@PostConstruct
	public void redisServer() throws IOException {
		final int port = isRedisRunning() ? findAvailablePort() : redisProperties.getPort();
		redisServer = new RedisServer(port);
		redisServer.start();
	}

	@PreDestroy
	public void stopRedis() {
		if (redisServer != null && redisServer.isActive()) {
			redisServer.stop();
		}
	}

	/**
	 * Embedded Redis가 현재 실행중인지 확인
	 */
	private boolean isRedisRunning() throws IOException {
		return isRunning(executeGrepProcessCommand(redisProperties.getPort()));
	}

	/**
	 * 현재 PC/서버에서 사용가능한 포트 조회
	 */
	public int findAvailablePort() throws IOException {

		for (int port = 10000; port <= 65535; port++) {
			final Process process = executeGrepProcessCommand(port);
			if (!isRunning(process)) {
				return port;
			}
		}

		throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
	}

	/**
	 * 해당 port를 사용중인 프로세스 확인하는 sh 실행
	 */
	private Process executeGrepProcessCommand(final int port) throws IOException {
		final String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
		final String[] shell = {"/bin/sh", "-c", command};
		return Runtime.getRuntime().exec(shell);
	}

	/**
	 * 해당 Process가 현재 실행중인지 확인
	 */
	private boolean isRunning(final Process process) {
		String line;
		final StringBuilder pidInfo = new StringBuilder();
		try (final BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			while ((line = input.readLine()) != null) {
				pidInfo.append(line);
			}
		} catch (final Exception ignore) {
		}
		return StringUtils.hasText(pidInfo.toString());
	}
}
