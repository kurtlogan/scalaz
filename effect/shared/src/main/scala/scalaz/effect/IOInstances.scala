// Copyright (C) 2017 John A. De Goes. All rights reserved.
package scalaz
package effect

import scalaz.typeclass.{ BindClass, MonadClass }

trait IOInstances {
  implicit def monad[E]: Monad[IO[E, ?]] =
    instanceOf(new MonadClass[IO[E, ?]] with BindClass.DeriveFlatten[IO[E, ?]] {
      override final def map[A, B](ma: IO[E, A])(f: A => B): IO[E, B] =
        ma.map(f)

      override final def ap[A, B](ma: IO[E, A])(mf: IO[E, A => B]): IO[E, B] =
        ma.flatMap(a => mf.map(f => f(a)))

      override final def pure[A](a: A): IO[E, A] = IO.now(a)

      override final def flatMap[A, B](ma: IO[E, A])(f: A => IO[E, B]): IO[E, B] =
        ma.flatMap(f)
    })
}
