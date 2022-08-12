osname=$(uname -s|tr A-Z a-z)
# 处理win下的osname
result=$(echo $osname|grep "mingw")
if [ "$result" != "" ]
then
	osname="win"
fi
# 处理其他名称
archname=$(uname -m|tr A-Z a-z)
version=$(git describe --tags `git rev-list --tags --max-count=1`)
dirname=spider-for-video-$osname-$archname-$version
# 编译打包
# 移动文件
mkdir $dirname
cp -r ./aria2 $dirname/aria2
cp spider-for-video.jar $dirname/spider-for-video.jar
cp spider-prop $dirname/spider-prop
cp spider-rec $dirname/spider-rec
cp start.cmd $dirname/start.cmd
# 压缩
tar -czvf $dirname.tar.gz $dirname
rm -r $dirname
# 移动文件夹
mkdir ../release/$version
# rm -r ../release/$1/$dirname
mv $dirname.tar.gz ../release/$version/$dirname.tar.gz