cmake_minimum_required(VERSION 3.4.1)
#配置指定路径的配置文件)
add_library(
             native-lib

             SHARED

             src/main/cpp/native-lib.cpp
             src/main/cpp/bspatch.c
             src/main/cpp/bzip/blocksort.c
             src/main/cpp/bzip/bzlib.c
             src/main/cpp/bzip/compress.c
             src/main/cpp/bzip/crctable.c
             src/main/cpp/bzip/decompress.c
             src/main/cpp/bzip/huffman.c
             src/main/cpp/bzip/randtable.c
             )

find_library(
              log-lib
              log )

target_link_libraries(
                       native-lib

                       ${log-lib} )