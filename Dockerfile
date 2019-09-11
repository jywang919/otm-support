 From tomcat:8.0.51-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*

#NW for ssh into the docker container on Azure -- https://docs.microsoft.com/en-us/azure/app-service/containers/tutorial-custom-docker-image
	#ENV SSH_PASSWD "root:Docker!"
	#-------------
	#RUN apt-get update \
		 #&& apt-get install -y --no-install-recommends dialog \
		        #&& apt-get update \
		  #&& apt-get install -y --no-install-recommends openssh-server \
		  #&& echo "$SSH_PASSWD" | chpasswd
	#-------------
	#RUN apk add --update openssh-server \	        	
	#-------------
	#  && echo "$SSH_PASSWD" | chpasswd
	
	#COPY sshd_config /etc/ssh/
	#EXPOSE 8080 2222
	#CMD ["service ssh start"]
	
#COPY ./build/libs/wangmobile-3.0.0.war /usr/local/tomcat/webapps/ROOT.war
#COPY ./wangmobile-3.0.0.war /usr/local/tomcat/webapps/wangmobile.war1
COPY ./build/libs/wangmobile.war /usr/local/tomcat/webapps/wangmobile.war
CMD ["catalina.sh","run"]

