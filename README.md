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


java, swing, gltf

mvn assembly:assembly
