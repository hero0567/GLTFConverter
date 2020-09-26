## glTF
glTF™ (GL Transmission Format) is a royalty-free specification for the efficient transmission and loading of 3D scenes and models by applications. glTF minimizes both the size of 3D assets, and the runtime processing needed to unpack and use those assets. glTF defines an extensible, common publishing format for 3D content tools and services that streamlines authoring workflows and enables interoperable use of content across the industry.

Please got more information from: https://en.wikipedia.org/wiki/GlTF

## GLTFConverter
GLTFConverter is a Java Swing tool to help user automate generate gltf to glb file and resolve cross platform key point relocation changed issue.
GLTFConverter will help user automate unzip, convert, zip to gernerate the final result.



1. 通过json的包修改gltf里面的值
2. 通过第三方工具包，通过gltf文件生成glb文件
3. 替换来的glb文件

## Workflow
1. Swing choose a folder user want to convert
2. Find all zip file in this folder
3. unzip file
4. change gltf file content by a formula
5. convert gltf to glb
6. zip all file
7. repeat step 4 until the last file

### 工程
mvn install:install-file  -DgroupId=de.javagl -DartifactId=jgltf-browser -Dversion=2.0.1-SNAPSHOT -Dpackaging=jar -Dfile=jgltf-browser-2.0.1-SNAPSHOT.jar
mvn install:install-file  -DgroupId=de.javagl -DartifactId=jgltf-impl-v1 -Dversion=2.0.1-SNAPSHOT -Dpackaging=jar -Dfile=jgltf-impl-v1-2.0.1-SNAPSHOT.jar
mvn install:install-file  -DgroupId=de.javagl -DartifactId=jgltf-impl-v2 -Dversion=2.0.1-SNAPSHOT -Dpackaging=jar -Dfile=jgltf-impl-v2-2.0.1-SNAPSHOT.jar
mvn install:install-file  -DgroupId=de.javagl -DartifactId=jgltf-impl-v2-technique-webgl -Dversion=2.0.1-SNAPSHOT -Dpackaging=jar -Dfile=jgltf-impl-v2-technique-webgl-2.0.1-SNAPSHOT.jar
mvn install:install-file  -DgroupId=de.javagl -DartifactId=jgltf-model -Dversion=2.0.1-SNAPSHOT -Dpackaging=jar -Dfile=jgltf-model-2.0.1-SNAPSHOT.jar
mvn install:install-file  -DgroupId=de.javagl -DartifactId=jgltf-obj -Dversion=2.0.1-SNAPSHOT -Dpackaging=jar -Dfile=jgltf-obj-2.0.1-SNAPSHOT.jar
mvn install:install-file  -DgroupId=de.javagl -DartifactId=jgltf-validator -Dversion=2.0.1-SNAPSHOT -Dpackaging=jar -Dfile=jgltf-validator-2.0.1-SNAPSHOT.jar
mvn install:install-file  -DgroupId=de.javagl -DartifactId=jgltf-viewer -Dversion=2.0.1-SNAPSHOT -Dpackaging=jar -Dfile=jgltf-viewer-2.0.1-SNAPSHOT.jar
mvn install:install-file  -DgroupId=de.javagl -DartifactId=jgltf-viewer-jogl -Dversion=2.0.1-SNAPSHOT -Dpackaging=jar -Dfile=jgltf-viewer-jogl-2.0.1-SNAPSHOT.jar
mvn install:install-file  -DgroupId=de.javagl -DartifactId=jgltf-viewer-lwjgl -Dversion=2.0.1-SNAPSHOT -Dpackaging=jar -Dfile=jgltf-viewer-lwjgl-2.0.1-SNAPSHOT.jar

java, swing, gltf

mvn assembly:assembly
