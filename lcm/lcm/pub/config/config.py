# Copyright 2016 [ZTE] and others.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
import os

# [MSB]
MSB_SERVICE_IP = '127.0.0.1'
MSB_SERVICE_PORT = '10080'

# [IMAGE LOCAL PATH]
ROOT_PATH = os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))
IMAGE_ROOT_PATH = os.path.join(ROOT_PATH, "/VmNfvo/VnfProduct")

# [REDIS]
REDIS_HOST = '127.0.0.1'
REDIS_PORT = '6379'
REDIS_PASSWD = ''

# [mysql]
DB_IP = "127.0.0.1"
DB_PORT = 3306
DB_NAME = "nfvo"
DB_USER = "root"
DB_PASSWD = "password"
