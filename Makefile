#

DEPS=build.gradle gradle.properties

.PHONY:	all
all:	build
	$(MAKE) jar

.PHONY: jar
jar:	../pymcfo.jar
	a="$$(basename "$$(readlink -e '$<')" .jar)" && echo "$$a"

# perhaps `make jar` now and then
.PHONY: auto
auto:	init
	-$(MAKE) build
	-$(MAKE) jar
	./gradlew -t build

# This apparently does not work, see https://stackoverflow.com/q/45387917
# Use `make auto` instead
#.PHONY: daemon
#daemon:
#	./gradlew --foreground

.PHONY:	dev
dev:	.gradle/init.dev

.PHONY: norm normal prod
norm normal prod clean::
	rm -f .gradle/init.dev

.PHONY:	build
build:	init
	./gradlew build

../pymcfo.jar:	build/libs/pymcfo-*[0-9].jar
	b='$<'; for a in build/libs/pymcfo-*[0-9].jar; do [ "$$b" -nt "$$a" ] || b="$$a"; done; rm -vf "$@"; ln -s --relative -v "$$b" '$@'; v="`basename "$$b" .jar`"; v="v`expr "$$v" : 'pymcfo-\(.*\)$$'`"; mkdir "builds/$$v" 2>/dev/null; cp -v -f "build/libs/pymcfo-$v"* "builds/$$v/"

.PHONY:	init
init:	.gradle/inited

.gradle/inited:	$(DEPS)
	./gradlew clean setupDecompWorkspace
	touch $@
	[ ! -f ./gradle/init.dev ] || $(MAKE) dev

.gradle/init.dev:	./gradle/inited	$(DEPS)
	./gradlew setupDevWorkspace
	touch $@

.PHONY: clean cleaner
clean::
	./gradlew clean
	rm -f .gradle/inited

cleaner:	clean
	rm -rf .gradle classes

