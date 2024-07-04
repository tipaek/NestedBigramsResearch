#include<bits/stdc++.h>

#define lld long long int

#define pb push_back

#define mk make_pair

using namespace std;

bool compare(const pair<lld,pair<lld,lld> >&a , const pair<lld,pair<lld,lld> >&b)

{

return a.second.second<b.second.second;

}

int main()

{

ios::sync_with_stdio(0);

cin.tie(0);

lld t;

cin>>t;

for(lld k=0 ; k<t ; k++)

{

vector< pair<lld ,pair<lld,lld> > > v;

lld n,x,y,jame=0,came=0;

cin>>n;

string a;

for(lld i=0 ; i<n ; i++)

{

cin>>x;

cin>>y;

v.pb(mk(i , mk(x,y)));

}

sort(v.begin(),v.end(),compare);

lld p=0;

a[v[p].first]='J';

v[p].first=-1;

jame++;

for(lld j=0 ; j<n ; j++)

{

if(v[j].second.first >= v[p].second.second && v[j].first!=-1 )

{

a[v[j].first]='J';

v[j].first=-1;

jame++;

p = j;

}

}

// sort(v.begin(),v.end(),compare);

lld p2;

for(lld f=0 ; f<n ; f++)

{

if(v[f].first!=-1)

{

a[v[f].first]='C';

v[f].first=-1;

came++;

p2=f;

break;

}

}

for(lld j=0 ; j<n ; j++)

{

if(v[j].second.first >= v[p2].second.second && v[j].first!=-1 )

{

a[v[j].first]='C';

v[j].first=-1;

came++;

p2=j;

}

}

cout<<"Case #"<<k+1<<": ";

if(jame+came != n)

cout<<"IMPOSSIBLE"<<"\n";

else

{

for(lld i=0 ; i<n ; i++)

cout<<a[i];

cout<<"\n";

}

}

}