#include <stdio.h>
#include <string.h>

int main(){
    int i, j, k=1, n, t, d, l, r, p, cnt, i0, in, ip;
    char c='a', string[101];
    scanf("%d", &t);
    while(t--){
        scanf("%s", string);
        n = strlen(string);
        printf("Case #%d: ", k++);
        p=0;
        d=string[0]-'0';
        for(i=0; i<d; i++){
            printf("(");
            p++;
        }
        printf("%c", string[0]);
        for(i=1; i<n; i++){
            i0 = string[i]-'0';
            ip = string[i-1]-'0';
            if(i0==ip){
                printf("%c", string[i]);
            }else if(i0>ip){
                d = i0-ip;
                for(j=0; j<d; j++){
                    printf("(");
                    p++;
                }
                printf("%c", string[i]);
            }else if(i0<ip){
                d = ip-i0;
                for(j=0; j<d; j++){
                    printf(")");
                    p--;
                }
                printf("%c", string[i]);
            }
        }

        for(j=0; j<p; j++){
            printf(")");
        }
        printf("\n");
    }
    return 0;
}
