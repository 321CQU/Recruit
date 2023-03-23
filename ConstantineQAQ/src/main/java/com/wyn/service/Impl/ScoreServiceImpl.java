package com.wyn.service.Impl;

import com.wyn.mapper.ScoreMapper;
import com.wyn.pojo.Course;
import com.wyn.pojo.Score;
import com.wyn.service.ScoreService;
import com.wyn.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ScoreServiceImpl implements ScoreService {

    @Override
    public void addCourse(Course course) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();

        SqlSession sqlSession = sqlSessionFactory.openSession();

        ScoreMapper mapper = sqlSession.getMapper(ScoreMapper.class);

        mapper.addCourse(course);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void deleteAll() {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();

        SqlSession sqlSession = sqlSessionFactory.openSession();

        ScoreMapper mapper = sqlSession.getMapper(ScoreMapper.class);

        mapper.deleteAll();
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public void addScore(Score score) {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();

        SqlSession sqlSession = sqlSessionFactory.openSession();

        ScoreMapper mapper = sqlSession.getMapper(ScoreMapper.class);

/*        mapper.addScore(score);*/
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<Score> selectAll() {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();


        SqlSession sqlSession = sqlSessionFactory.openSession();


        ScoreMapper mapper = sqlSession.getMapper(ScoreMapper.class);

        List<Score> scores = mapper.selectAll();

        sqlSession.close();

        return scores;
    }
}
