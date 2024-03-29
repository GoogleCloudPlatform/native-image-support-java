#!/bin/bash
# Copyright 2019 Google LLC
#
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA

set -eo pipefail

# Use Java 11.
export JAVA_HOME=${KOKORO_GFILE_DIR}
export PATH="$JAVA_HOME/bin:$PATH"
$JAVA_HOME/bin/javac -version

dir=$(dirname "$0")

pushd $dir/../
./mvnw install -B -V -DskipTests
popd

source $dir/release_snapshot.sh
