function fv=ft_mod_base(base,w,v)
%   ģbase�µĸ���Ҷ�任,baseΪ����
%   wΪ����Ԫ,��Ȼ����
%   vΪҪ�任������
    n=numel(v);
    M=ft_mod_matrix(w,n,base)
    fv=mod(M*v,base);
end