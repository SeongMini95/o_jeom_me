import React, { useEffect, useRef, useState } from 'react';
import { Link, useLocation, useNavigate } from "react-router-dom";
import { BROWSER_PATH } from "../../../constants/path";
import style from '../../../css/Layout/Default/Header.module.css';
import { useRecoilState, useResetRecoilState, useSetRecoilState } from "recoil";
import { accessTokenState, loginState, refreshTokenState } from "../../../recoils/auth";
import { userInfoState } from "../../../recoils/user";
import { positionState } from "../../../recoils/position";
import authApi from "../../../api/auth";
import { positionProvider } from "../../../utils/positionUtils";

const Header = () => {
    const navigate = useNavigate();
    const location = useLocation();

    const [isLogin, setIsLogin] = useRecoilState(loginState);
    const [{ nickname }, setUserInfo] = useRecoilState(userInfoState);
    const [accessToken, setAccessToken] = useRecoilState(accessTokenState);
    const setRefreshToken = useSetRecoilState(refreshTokenState);
    const resetUserInfo = useResetRecoilState(userInfoState);
    const setPosition = useSetRecoilState(positionState);

    const [userModal, setUserModal] = useState(false);
    const refUserModal = useRef(null);

    useEffect(() => {
        const authCheck = async () => {
            try {
                if (!accessToken) {
                    return;
                }

                const { result, userId, nickname, profile } = await authApi.check();
                if (result) {
                    setIsLogin(true);
                    setUserInfo({
                        userId,
                        nickname,
                        profile
                    });
                } else {
                    handlerLogout();
                }
            } catch (e) {
                handlerLogout();
            }
        }

        const handlerResetPosition = () => {
            setPosition({
                codes: {
                    region1: '1100000000',
                    region2: '',
                    region3: ''
                },
                lastCode: '1100000000',
                address: '서울',
                x: '126.978652258309',
                y: '37.566826004661'
            });
        }

        const positionCheck = () => {
            const position = positionProvider.getPosition();
            if (position) {
                const objPosition = JSON.parse(position);
                if (!objPosition.codes.region1 && !objPosition.codes.region2 && !objPosition.codes.region3) {
                    handlerResetPosition();
                    return;
                }

                if (!objPosition.lastCode || !objPosition.address || !objPosition.x || !objPosition.y) {
                    handlerResetPosition();
                    return;
                }

                setPosition(JSON.parse(position));
            } else {
                setPosition({
                    codes: {
                        region1: '1100000000',
                        region2: '',
                        region3: ''
                    },
                    lastCode: '1100000000',
                    address: '서울',
                    x: '126.978652258309',
                    y: '37.566826004661'
                });
            }
        }

        authCheck();
        positionCheck();
    }, []);

    useEffect(() => {
        const handlerClickOutside = (e) => {
            if (refUserModal.current && !refUserModal.current.contains(e.target)) {
                setUserModal(false);
            }
        }

        document.addEventListener('mousedown', handlerClickOutside);

        return () => {
            document.removeEventListener('mousedown', handlerClickOutside);
        };
    }, [refUserModal]);

    const handlerLogout = () => {
        setIsLogin(false);
        setAccessToken('');
        setRefreshToken('');
        resetUserInfo();

        navigate(BROWSER_PATH.BASE);
    };

    const handlerClickLogin = (e) => {
        e.preventDefault();

        const rtnUri = location.pathname;
        const loginPaths = [BROWSER_PATH.AUTH.LOGIN, BROWSER_PATH.AUTH.NAVER_LOGIN, BROWSER_PATH.AUTH.KAKAO_LOGIN];
        if (!loginPaths.includes(rtnUri)) {
            sessionStorage.setItem('rtnUri', rtnUri);
        }

        navigate(BROWSER_PATH.AUTH.LOGIN);
    }

    const handlerClickLogout = (e) => {
        e.preventDefault();
        handlerLogout();
    }

    return (
        <>
            <header className={style.header_layout}>
                <div className={style.header_content}>
                    <div className={style.header_wrap}>
                        <div className={style.main_logo}>
                            <Link to={BROWSER_PATH.BASE}>
                                <img className={style.logo_img} src={`${process.env.PUBLIC_URL}/assets/logo/logo_transparent.png`} alt="" />
                            </Link>
                        </div>
                        {!isLogin ? (
                            <div className={style.header_link}>
                                <a href={'#none'} className={style.link_login} onClick={handlerClickLogin}>로그인</a>
                            </div>
                        ) : (
                            <div className={style.user_wrap}>
                                <button className={style.user_button} ref={refUserModal} onClick={() => setUserModal(!userModal)}>
                            <span className={style.user_content}>
                                <span className={style.user_name}>{nickname}</span>님
                            </span>
                                    <img className={style.user_button_ico} src={`${process.env.PUBLIC_URL}/assets/image/arrow.png`} alt="" />
                                    {userModal && (
                                        <div className={style.user_modal}>
                                            <Link to={BROWSER_PATH.USER.MY_INFO}>나의 정보</Link>
                                            <a href={'#none'} onClick={handlerClickLogout}>로그아웃</a>
                                        </div>
                                    )}
                                </button>
                                <Link className={style.link_write_review} to={BROWSER_PATH.STORE.SEARCH_PLACE_LIST}>리뷰 작성</Link>
                            </div>
                        )}
                    </div>
                </div>
            </header>
            <nav className={style.empty_nav} />
        </>
    );
};

export default Header;