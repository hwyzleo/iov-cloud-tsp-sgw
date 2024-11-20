pipeline {
    agent any

    environment {
        PROJECT_NAME = "${env.JOB_NAME}"
        IMAGE_NAME = "${env.REGISTRY_URL}/${PROJECT_NAME}:${env.BUILD_NUMBER}"
    }

    stages {
        stage('构建镜像') {
            steps {
                script {
                    sh "docker build -t ${IMAGE_NAME} -f ../Dockerfile ."
                }
            }
        }
        stage('上传镜像') {
            steps {
                sh "docker push ${IMAGE_NAME}"
            }
        }
        stage('运行镜像') {
            steps {
                sh "docker stop ${PROJECT_NAME}"
                sh "docker rm ${PROJECT_NAME}"
                sh "docker pull ${IMAGE_NAME}"
                sh "docker run -d --name ${PROJECT_NAME} ${IMAGE_NAME}"
                sh "sleep 5"
                sh "docker logs ${PROJECT_NAME}"
            }
        }
    }
}