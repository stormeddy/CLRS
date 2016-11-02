% L=[1 0 0;0.2 1 0;0.6 0.5 1];
% U=[5 6 3;0 0.8 -0.6;0 0 2.5];
% pi=[3 1 2];
% b=[3 7 8];
% x=lup_solve(L,U,pi,b);
% 
% A=[2 3  1 5
%    6 13 5 19
%    2 19 10 23
%    4 10 11 31];
% [L,U]=lu_decomposition(A);
% 
% B=[2 0  2   0.6
%    3 3  4   1
%    5 5  4   2
%   -1 -2 3.4 -1];
% [L,U,pi]=lup_decomposition(B)
% P=pi_to_P(pi)
% 
% C=[4  -5  6
%    8  -6  7
%    12 -7 12];
% [L,U,pi]=lup_decomposition(C);
% P=pi_to_P(pi);
% 
% % 28.1-3
% A=[1 5 4
%    2 0 3
%    5 8 2];
% b=[12;9;5];
% x=lup_solve_linear_equation(A,b);
% 
% 
% D=[1  0  0
%    4  1  0
%    -6 5  1];
% [L,U,pi]=lup_decomposition(D);
% P=pi_to_P(pi);
% 
% % 28.1-4
% E=diag(rand(6,1));
% [L,U,pi]=lup_decomposition(E);
% P=pi_to_P(pi);

% Fi=randperm(6);
% F=pi_to_P(Fi);
% [L,U,pi]=lup_decomposition(F);
% P=pi_to_P(pi);

% A=[4  -5  6
%    8  -6  7
%    12 -7 12];
% inv(A)
% inverse(A)


% A=[1  -1  1
%    1   1  1
%    1   2  4
%    1   3  9
%    1   5  25];
% B=pinv(A)
% C=inverse(A)
% B*A
% C*A


% 28-1
% A=[1 -1 0  0 0
%    -1 2 -1 0 0
%    0 -1 2 -1 0
%    0 0 -1 2 -1
%    0 0  0 -1 2];
% [L,U]=lu_decomposition(A)
% b=[1 1 1 1 1]';
% x=lup_solve_linear_equation(A,b)
% B=inverse(A)
% inv(A)


x=0:16;
y=tan(pi*x/20);
xi=linspace(0,16);
yi=spline(x,y,xi);
figure
plot(x,y,'o',xi,yi)