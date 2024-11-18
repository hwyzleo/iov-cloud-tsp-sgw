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
                    sh "docker build --network=my_network -t ${IMAGE_NAME} -f ../Dockerfile ."
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
                sh "docker pull ${IMAGE_NAME}"
                sh "docker run -d  --network=my_network --name ${PROJECT_NAME} ${IMAGE_NAME}"
            }
        }
    }
}