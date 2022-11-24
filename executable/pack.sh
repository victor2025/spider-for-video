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
# 判断执行脚本
if [ "$osname" == "win" ]
then
	cp start.cmd $dirname/start.cmd
	cp clear.cmd $dirname/clear.cmd
	rm $dirname/aria2/aria2c
else
	cp start.sh $dirname/start.sh
	cp clear.sh $dirname/clear.sh
	rm $dirname/aria2/aria2c.exe
fi
# 压缩
tar -czvf $dirname.tar.gz $dirname
# 移动文件夹
mkdir ../release
mkdir ../release/$version
# rm -r ../release/$1/$dirname
mv $dirname.tar.gz ../release/$version/$dirname.tar.gz
# 带jre版本
if [ "$osname" == "win" ]
then
	rm $dirname/start.cmd
	cp start-jre.cmd $dirname/start.cmd
	cp -r ./jre $dirname/jre
	# 重命名
	dirname_jre=spider-for-video-$osname-$archname-jre-$version
	mv $dirname $dirname_jre
	# 压缩
	tar -czvf $dirname_jre.tar.gz $dirname_jre
	mv $dirname_jre.tar.gz ../release/$version/$dirname_jre.tar.gz
	rm -r $dirname_jre
else
	rm -r $dirname
fi
