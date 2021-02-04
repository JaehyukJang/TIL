# [Tacademy] 아파치 스파크 입문

- 목차
- 참고 자료

    [아파치 스파크 입문 | T아카데미 온라인강의](https://tacademy.skplanet.com/live/player/onlineLectureDetail.action?seq=193)

# [1강] Spark의 개념과 활용

## Apache Hadoop

- 특정 기술이라기보다는 하둡 클러스터 내지는 하둡 환경을 통칭
    - Hadoop Distributed File System(HDFS) : 분산 파일 시스템
    - YARN : 여러 어플리케이션을 돌리기 위해 리소스를 관리하는 클러스터 매니저
    - Hadoop MapReduce : 분산 프로세싱
    - Hadoop Common : 모니터링, 코디네이터, 파이프라이닝, SQL 기술 등

## HDFS

- Google File System(GFS)에서 2004년 시작
- **하둡 에코시스템에서 분산 파일 시스템**을 지원
- **큰 파일들을 저장하고 처리**될 수 있도록 지원
- Hardware failures에 대처할 수 있는 신뢰성

    → 병렬처리에서 문제가 생겼을 때 재연산을 하기 위해 HDFS에서 copy를 저장

- 작고 많은 파일에 취약하며, 디스크 I/O로 인한 latency
- HDFS의 구조
    - Master - Slave

        ![design_of_hadoop](./img/design_of_hadoop.png)

    - Master Node : 메타 데이터(어떤 Data Node에 어떤 block 단위로 저장되어 있는지)를 관리하는 Name Node

        → 데이터는 Data Nodes에 block 단위로 저장이되지만, Name Node에 장애가 발생하면 모든 메타 데이터가 없어지기 때문에 고가용성 보장 필요

    - Slave Nodes : 실제로 데이터를 block 단위로 저장하는 Data Nodes
- Block
    - **파일을 block 단위로 나누고, 해당 단위로 read/write**
    - Replication of Blocks : 3 copy(default)
        - fault tolerance(장애에 대한 복구)를 위해 파일을 copy

            → 온프리미스 기준으로 Data Node가 죽는 일이 많음 : disk failed, 네트워크 문제, OS fault 등

        - 잘 분배되어 저장되어 있을 경우 병렬성이 증가함

## Hadoop MapReduce

- 하둡 에코시스템의 기본적인 프로그래밍 패러다임
- 전통적인 병렬 프로그래밍에선 전문성이 필요(멀티 스레드, 동기화, 정확성/성능fault toerance 보장의 어려움 등)

    → MapReduce : 코드를 병렬적으로 돌리기 쉬운 프로그래밍 모델

## Apache Spark

- **기존 MapReduce 프레임워크보다 빠르고 범용적으로 사용하는 컴퓨팅 시스템**
    - MapReduce의 경우 disk I/O로 인하여 느리지만, Spark는 In-memory 방식으로 처리하여 network I/O나 disk I/O를 줄일 수 있음
    - MapReduce는 low-level API를 활용하여 더 많은 핸들링을 할 수 있지만, Spark는 Scala 언어로 개발되어 함수형 언어의 장점을 가지기 때문에 코드가 간결하고 개발의 생산성이 높음
- 반복적으로 데이터에 접근하여 연산하는 머신러닝에 적합
- **컴퓨터 클러스터에서 병렬 데이터 프로세싱을 하는 라이브러리의 집합이자 통합 컴퓨팅 엔진**

    ![apache_spark](apache_spark.png)

- 다양한 소스에서 다양한 형태의 데이터를 읽고 쓰며, 데이터 분석/머신러닝/그래프 분석/ 실시간 스트리밍 데이터 처리 등의 작업 지원

    ![support_of_spark.png](./img/support_of_spark-submit.png)

- 기본 아키텍처

    ![architecture_of_spark](architecture_of_spark.png)

    1. spark-submit 통해서 Cluster Manage에 user code를 제출

        (리소스를 얼마나 쓸지 지정 : Executor 개수, Core 수, Memory 수)

    2. Driver Process가 생성되고, Driver Process에서는 Spark Session 생성
    3. 다수의 Executors에 코드를 할당하여 실행
- 활용 케이스
    - Streaming Data
    - Machine Learning
    - Interactive Analysis → latency가 적기 때문에 빠른 분석 가능
    - Data Warehousing
    - Batch Processing → 대부분은 Hive로 많이 수행
    - Exploratory Data Analysis(EDA)
    - Graph Data Analysis
    - Spatial(GIS) Data Analysis
- Spark은 컴퓨팅 프레임워크로 HDFS나 DB를 대체하는 것이 아니고, 모든 케이스에 적절하지도 않음
    - **단순한 케이스에는 데이터와 복잡성과 안정성 측면에서 MapReduce와 Hive가 더 적절할 수 있음**
    - BI와 같은 multi-user 환경으로 설계되지 않음

        → storage에 있는 데이터를 읽기 때문에 disk I/O 발생하고 DB만큼의 성능이 나오진 않음

    - user가 늘어나면 **메모리 사용에 신경을 많이 써야함**