FROM ubuntu:latest

# updates the package lists for upgrades and new packages installations. then it installs neccesary tools:
# wget (a free utility for non-iteractive download of files from the web) and software-properties-common
# (A package that provides abstraction of the used apt repository)
RUN apt-get update && \
    apt-get install -y wget software-properties-common

# adds openJDK PPA (personal package archive) to your apt sources, updates the package lists again, and then
# installs openJDK 17
RUN add-apt-repository ppa:openjdk-r/ppa && \
    apt-get update && \
    apt-get install -y openjdk-17-jdk

# Verify Java installation
RUN java -version

# Set the working directory in the container to /app
# all subsequent commands will be run from this directory
WORKDIR /app

# Copy the current directory contents (on your system) into the container at /app
COPY . /app

# Compiles all java files in the src/stream directory
RUN javac src/Collections/*.java

# Change to src directory
WORKDIR /app/src

# Run the app when the container launches
CMD ["java", "Collections/UsingLists"]


# This is how to do it without the ubuntu base image
# FROM openjdk:17-jdk-slim
# WORKDIR /app
# COPY . /app
# RUN javac streams/*.java
# CMD ["java", "streams/Streams"]