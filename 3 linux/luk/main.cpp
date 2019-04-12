#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/shm.h>
#include <sys/wait.h>
#include <unistd.h>
#include <cstring>
#include <string.h>
#include <cstdlib>
#include <cstdio>
#include <iostream>
#include <limits>

#define KEY_NAME "/dev/null"
#define SERVER_SEMAPHORE_INDEX 0
#define CLIENT_SEMAPHORE_INDEX 1
#define CLIENT_ERROR_SEMAPHORE_INDEX 2
#define KILL_SEMAPHORE_INDEX 3
#define SIZE 20

using namespace std;

int main(int argc, char** argv)
{
    key_t keySem, keyShared;
    int semId, sharedId;
    void* memoryAdress;
    struct sembuf semStruct;
    struct shmid_ds memoryStruct;

    keySem=atoi(argv[1]);
    keyShared=atoi(argv[2]);
  //  cout<<"(LUK)"<<keySem<<argv[1]<<endl;
   // cout<<"(LUK)"<<keyShared<<argv[2]<<endl;


    //создание семафора ключ, количество семафор, создание
    semId = semget(keySem, 4,  SHM_R | SHM_W);
    if(semId==-1)
    {
        cout<<"semget YMER (LUK)"<<endl;
        return 0;
    }

    //создание памяти
    sharedId = shmget(keyShared, SIZE,  SHM_R | SHM_W);
    if(sharedId==-1)
    {
        cout<<"shmget YMER (LUK)"<<endl;
        //удаление всех ожидающих семафор начиная с 0
        semctl(semId, 0, IPC_RMID, NULL);
        return 0;
    }

    //адрес памяти
    memoryAdress=shmat(sharedId, NULL, 0);
    if(memoryAdress==NULL)
    {
        cout<<"Cheto ne to s pam'at'u (LUK)"<<endl;
        semctl(semId, 0, IPC_RMID, NULL);
        shmctl(sharedId, IPC_RMID, &memoryStruct);
        return 0;
    }

    /*semStruct.sem_num=SERVER_SEMAPHORE_INDEX;
    semStruct.sem_op=1;
    semStruct.sem_flg=SEM_UNDO;
    semop(semId, &semStruct, 1);
    semStruct.sem_num=CLIENT_SEMAPHORE_INDEX;
    semStruct.sem_op=1;
    semStruct.sem_flg=SEM_UNDO;
    semop(semId, &semStruct, 1);*/

    /*cout<<"(LUK)"<<semctl(semId, SERVER_SEMAPHORE_INDEX, GETVAL)<<endl;
    //cout<<"(LUK)"<<sharedId<<"   "<<argv[2]<<endl;
    cout<<"(LUK)"<<semctl(semId, CLIENT_SEMAPHORE_INDEX, GETVAL)<<endl;
    cout<<"(LUK)"<<semctl(semId, CLIENT_ERROR_SEMAPHORE_INDEX, GETVAL)<<endl;
    cout<<"(LUK)"<<semctl(semId, KILL_SEMAPHORE_INDEX, GETVAL)<<endl;*/


    while(true)
    {
        //ожидание сервера
        //индекс сервера
        semStruct.sem_num=SERVER_SEMAPHORE_INDEX;
        semStruct.sem_op=0;
        semStruct.sem_flg=SEM_UNDO;
        semop(semId, &semStruct, 1);
        semStruct.sem_num=SERVER_SEMAPHORE_INDEX;
        semStruct.sem_op=1;
        semStruct.sem_flg=SEM_UNDO;
        semop(semId, &semStruct, 1);

        //  cout<<endl<<i<<endl;

        //ожидание инфы
        if(semctl(semId, KILL_SEMAPHORE_INDEX, GETVAL)==1)
        {
            shmdt(memoryAdress);
            return 0;
        }


        //походу чтение памяти
        memoryAdress=(char*)shmat(sharedId, NULL, 0);


        if(memoryAdress==NULL)
        {
            semStruct.sem_num=CLIENT_ERROR_SEMAPHORE_INDEX;
            semStruct.sem_op=1;
            semStruct.sem_flg=SEM_UNDO;
            semop(semId, &semStruct, 1);

            semStruct.sem_num=CLIENT_SEMAPHORE_INDEX;
            semStruct.sem_op=-1;
            semStruct.sem_flg=SEM_UNDO;
            semop(semId, &semStruct, 1);

            return 1;
        }

        cout<<"A Client takoi tip: "<<(char*)memoryAdress<<endl;

        semStruct.sem_num=CLIENT_SEMAPHORE_INDEX;
        semStruct.sem_op=-1;
        semStruct.sem_flg=SEM_UNDO;
        semop(semId, &semStruct, 1);
    }
    return 0;



    return 0;
}