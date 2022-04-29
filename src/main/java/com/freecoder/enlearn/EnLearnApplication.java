package com.freecoder.enlearn;

import com.freecoder.enlearn.model.Word;
import com.freecoder.enlearn.utils.FileUtils;
import com.freecoder.enlearn.utils.HttpHelper;
import com.freecoder.enlearn.utils.YoudaoWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class EnLearnApplication implements CommandLineRunner {

	@Autowired
	FileUtils fileUtils;

	@Autowired
	YoudaoWeb youdaoWeb;

	@Autowired
	HttpHelper httpHelper;

	public static void main(String[] args) {
		SpringApplication.run(EnLearnApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<String> wordList = fileUtils.loadData();
		ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
		for (String word : wordList) {
			pool.submit(() ->{
				try {
					Word obj = youdaoWeb.analyse(word);
					fileUtils.persistWord(obj);
					byte[] bts = httpHelper.getMp3(word);
					fileUtils.persistAudio(word, bts);
				} catch (IOException e) {
					e.printStackTrace();
					log.error("{} error", word);
				} finally {
					log.info("{} finish!", word);
				}
			});
		}
	}
}
