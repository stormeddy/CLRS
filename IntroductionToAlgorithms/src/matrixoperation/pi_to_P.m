function [P]=pi_to_P(pi)
    n=size(pi,2);
    P=zeros(n);
    for i=1:n
        P(i,pi(i))=1;
    end
end