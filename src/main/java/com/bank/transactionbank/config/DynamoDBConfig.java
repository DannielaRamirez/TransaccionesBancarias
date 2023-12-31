package com.bank.transactionbank.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
	@EnableDynamoDBRepositories
	    (basePackages = "com.bank.transactionbank.repository")
	public class DynamoDBConfig {

	    @Value("${aws.dynamoDBUrl}")
	    private String amazonDynamoDBEndpoint;

	    @Value("${aws.accessKey}")
	    private String amazonAWSAccessKey;

	    @Value("${aws.secretKey}")
	    private String amazonAWSSecretKey;

	    @Bean
	    public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider) {
	        AmazonDynamoDB amazonDynamoDB
	            = AmazonDynamoDBClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, "us-west-2"))
	            .withCredentials(awsCredentialsProvider).build();
	        return amazonDynamoDB;
	    }

	    @Bean
	    public AWSCredentialsProvider awsCredentialsProvider() {
	        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
	    }
}
