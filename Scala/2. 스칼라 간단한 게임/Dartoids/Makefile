#
#       A monolithic Makefile that recompiles (see SCALAC) all discoverable sources
#       if ANY were edited later than the timestamp (.compiled). 
#
SOURCE := $(wildcard */*.scala)
LIB    := $(HOME)/local/scalalib
CP     := $(LIB)/app.jar:$(LIB)/scala-swing.jar:$(LIB)/scalacheck.jar

#       RUN specifies the name of the (local) script that runs scala using the 
#           compilation classpath augmented by the compiler's destination folder
#       BIN specifies the compiler's destination folder
#       DOC specifies the folder into which the documentation is generated
#       $(DOC)/index.html is the root of the documentation tree.

BIN    := bin
DOC    := doc
RUN    := dartoids
MAIN   := ox.games.Dartoids

#       Parameters: compiler, flags, documentation program
#
#       SCALAC specifies the scala compiler: it can be "fsc" (the "fast" compiler) or "scalac"
#       SFLAGS specifies the flags given to the scala compiler
#

SCALAC   := scalac
SFLAGS   := -deprecation -feature
SCALADOC := scaladoc -deprecation -groups -sourcepath src

#       Targets. The default target is "all"

all:        .compiled $(RUN)

.compiled:  $(SOURCE); mkdir -p bin; $(SCALAC) $(SFLAGS) -cp $(CP) -d $(BIN) $(SOURCE); touch $@

$(DOC):     $(SOURCE); mkdir -p $(DOC) ; $(SCALADOC) -cp $(CP) -d $(DOC) $(SOURCE) ; touch $@

$(RUN):     ; echo scala -cp $(CP):$(BIN) $(MAIN) '"'$$\@'"' > ./$(RUN); chmod +x $(RUN)

.PHONY:     clean
clean:      ; rm -rf .compiled $(BIN) $(DOC) $(RUN) out *~

#       For straightforward testing on a Linux machine
spart:      bin; scp -r bin spartacus.local:IP3-Program


