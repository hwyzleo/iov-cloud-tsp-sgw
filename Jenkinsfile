pipeline {
    agent any
    def imageName

    stages {
        stage('构建镜像') {
            steps {
                script {
                    def projectName = env.JOB_NAME
                    imageName = "${env.REGISTRY_URL}/${projectName}:${env.BUILD_NUMBER}"
                    sh "docker build -t ${imageName}."
                }
            }
        }
        stage('上传镜像') {
            steps {
                sh "docker push ${imageName}"
            }
        }
        stage('运行镜像') {
            steps {
                sh "docker pull ${imageName}"
                sh "docker run -d --name ${projectName} ${imageName}"
            }
        }
    }
}